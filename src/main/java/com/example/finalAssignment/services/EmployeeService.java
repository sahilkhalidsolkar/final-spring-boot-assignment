package com.example.finalAssignment.services;

import com.example.finalAssignment.dtos.EmployWeatherDto;
import com.example.finalAssignment.dtos.Weather;
import com.example.finalAssignment.exception.GenericException;
import com.example.finalAssignment.interfaces.WeatherClient;
import com.example.finalAssignment.models.Employee;
import com.example.finalAssignment.repository.EmployeeRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
//@AllArgsConstructor
public class EmployeeService {
    @Value("${spring.mail.username}")
    private String sender;

    private WeatherClient weatherClient;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private EmployeeRepository employeeRepository;

    public EmployeeService(JavaMailSender javaMailSender, TemplateEngine templateEngine, WeatherClient weatherClient, EmployeeRepository employeeRepository) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.weatherClient = weatherClient;
        this.employeeRepository = employeeRepository;
    }

    public Employee addEmployee(Employee employee, MultipartFile file) {

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path destPath = Path.of("uploads/" + fileName);
        Set<String> validFile = new HashSet<>(Arrays.asList("image/jpeg", "image/jpg", "image/png"));
        if (!validFile.contains(file.getContentType())) {
            throw new GenericException("Invalid file format");
        }


        try {
            Files.copy(file.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new GenericException("Unable to uppload the file ");
        }

        employee.setPhotoFile(destPath.toString());
        return employeeRepository.save(employee);

    }

    public EmployWeatherDto getEmployeeWithWeather(Integer id) {
        System.out.println(id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new GenericException("the employee does not exists"));
        Weather[] weather = weatherClient.getWeatherOfEmployee(employee.getLocation());
        if (weather.length == 0) throw new GenericException("PLease enter valid location");

        EmployWeatherDto employWeatherDto = new EmployWeatherDto();
        employWeatherDto.setName(employee.getName());
        employWeatherDto.setLocation(employee.getLocation());
        employWeatherDto.setId(employee.getId());
        employWeatherDto.setEmail(employee.getEmail());
        employWeatherDto.setPhone(employee.getPhone());
        employWeatherDto.setPhotoFile(employee.getPhotoFile());
        employWeatherDto.setWeather(weather[0]);

        System.out.println(employWeatherDto);
        return employWeatherDto;


    }

    public void writeEmployeeToCSVFile(String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);) {
            List<Employee> allEmp = employeeRepository.findAll();
            allEmp.forEach((employee -> {
                String empStr = employee.getId() + "," + employee.getName() + "," + employee.getEmail() + "," + employee.getPhone() + "," + employee.getLocation() + "," + employee.getPhotoFile() +","+employee.getSalary()+ "\n";
                try {
                    bufferedWriter.write(empStr);
                } catch (IOException e) {
                    throw new GenericException("Unable to write to csv file");
                }
            }));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void importFromCsvToDb(String fileName) {

        Path sourcePath = Path.of(fileName);
        try {
            Files.lines(sourcePath).map((employee) -> {
                String[] employStr = employee.split(",");
                String name = employStr[1];
                String email = employStr[2];
                String phone = employStr[3];
                String location = employStr[4];
                String photoFile = employStr[5];
                Integer salary = Integer.parseInt(employStr[6]);

                System.out.println("importing");
                Employee emp = new Employee();
                emp.setName(name);
                emp.setEmail(email);
                emp.setPhone(phone);
                emp.setLocation(location);
                emp.setPhotoFile(photoFile);
                emp.setSalary(salary);

                return emp;
            }).forEach(employeeRepository::save);


        } catch (IOException e) {
            throw new GenericException("Problem in importing the file to db");
        }


    }

    public void sendEmail() {
        System.out.println(sender);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        SimpleDateFormat formatMonth = new SimpleDateFormat("MMMMM");

        employeeRepository.findAll().forEach(employee ->{
            Context context = new Context();
            HashMap<String, Object> contextData = new HashMap<>();

            contextData.put("employee",employee);
            contextData.put("HRA",4000);
            contextData.put("Conveyance",1000);
            contextData.put("MedicalAllowance",10000);
            contextData.put("creditDate",formatter.format(date));
            contextData.put("month",formatMonth.format(date));



            context.setVariables(contextData);
            String html = templateEngine.process("SalaryCredited", context);
            System.out.println(html);


            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
                mimeMessageHelper.setFrom(sender);
                mimeMessageHelper.setTo(employee.getEmail());
                mimeMessageHelper.setSubject("Salary Credited");
                mimeMessageHelper.setText(html,true);
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } );

    }

}
