package ro.ubb.web.controller;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.core.model.Exceptions.MyException;
import ro.ubb.core.service.IStudentService;
import ro.ubb.web.converter.StudentConverter;
import ro.ubb.web.dto.StudentDto;
import ro.ubb.web.dto.StudentsDto;

@RestController
public class StudentController {
    public static final Logger log= LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private IStudentService studentService;

    @Autowired
    private StudentConverter studentConverter;

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    StudentsDto getStudents() {
        log.trace("begin get students");
        StudentsDto cpy = new StudentsDto(studentConverter
                .convertModelsToDtos(studentService.getAllStudents()));
        log.trace("end get students={}",cpy);
        return cpy;
    }

    @RequestMapping(value = "/students/sort", method = RequestMethod.GET)
    StudentsDto getStudentsSorted() {
        log.trace("begin sort students");
        StudentsDto cpy = new StudentsDto(studentConverter
                .convertModelsToDtos(studentService.sortStudentsAscendingByName()));
        log.trace("end sort students={}",cpy);
        return cpy;
    }
    @RequestMapping(value = "/students/filter/{name}", method = RequestMethod.GET)
    StudentsDto getStudentsFiltered(@PathVariable String name) {
        log.trace("begin filter students name={}",name);
        StudentsDto cpy = new StudentsDto(studentConverter
                .convertModelsToDtos(studentService.filterStudentsByName(name)));
        log.trace("end filter students={}",cpy);
        return cpy;
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    ResponseEntity<?> saveStudent(@RequestBody StudentDto studentDto) {
        log.trace("begin add student={}", studentDto);
        try {
            studentService.addStudent(
                   studentConverter.convertDtoToModel(studentDto)
           );
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MyException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @RequestMapping(value = "/students", method = RequestMethod.PUT)
    ResponseEntity<?> updateStudent(@RequestBody StudentDto studentDto) {
        log.trace("begin update student={}", studentDto);
        try {
            studentService.updateStudent(studentConverter.convertDtoToModel(studentDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MyException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteStudent(@PathVariable Long id){
        log.trace("begin delete student with id={}", id);
        try {
            studentService.deleteStudent(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MyException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value="/students/passed",method = RequestMethod.GET)
    StudentsDto getStudentsWhoPassed(){
        log.trace("getStudentsWhoPassed - method entered");
        return new StudentsDto(studentConverter.convertModelsToDtos(
                studentService.getStudentsWhoPassed()
        )
        );
    }

    @SneakyThrows
    @RequestMapping(value = "/students/mostproblems", method = RequestMethod.GET)
    StudentDto getStudentWithMostProblems(){
        log.trace("getStudentsWithMostProblems - method entered");
        return  studentConverter.convertModelToDto(
                studentService.getStudentsWithMostProblems()
        );
    }
}
