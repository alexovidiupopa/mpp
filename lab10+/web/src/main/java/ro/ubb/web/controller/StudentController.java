package ro.ubb.web.controller;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.ubb.core.service.IStudentService;
import ro.ubb.core.service.StudentService;
import ro.ubb.web.converter.StudentConverter;
import ro.ubb.web.dto.StudentDto;
import ro.ubb.web.dto.StudentsDto;

public class StudentController {
    public static final Logger log= LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private IStudentService studentService;

    @Autowired
    private StudentConverter studentConverter;


    @RequestMapping(value = "/students", method = RequestMethod.GET)
    StudentsDto getStudents() {
        //todo: log
        return new StudentsDto(studentConverter
                .convertModelsToDtos(studentService.getAllStudents()));

    }

    @SneakyThrows
    @RequestMapping(value = "/students", method = RequestMethod.POST)
    ResponseEntity<?> saveStudent(@RequestBody StudentDto studentDto) {
        //todo log
         studentService.addStudent(
                studentConverter.convertDtoToModel(studentDto)
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SneakyThrows
    @RequestMapping(value = "/students", method = RequestMethod.PUT)
    ResponseEntity<?> updateStudent(@RequestBody StudentDto studentDto) {
        //todo: log
        studentService.updateStudent(studentConverter.convertDtoToModel(studentDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SneakyThrows
    @RequestMapping(value = "/students", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteStudent(@RequestBody StudentDto studentDto){
        //todo:log

        studentService.deleteStudent(studentConverter.convertDtoToModel(studentDto));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
