package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.StudentPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.StudentRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.StudentResponse;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.StudentMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Company;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Student;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.CompanyRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final CompanyRepository companyRepository;

    public StudentResponse findById(Integer id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id:%d".formatted(id)));
        return studentMapper.toStudentResponse(student);
    }

    public int create(StudentRequest request) {
        Company company = companyRepository.findById(request.companyId())
                        .orElseThrow(() -> new EntityNotFoundException("Company not found with id:%d".formatted(request.companyId())));

        Student student = studentMapper.toStudentEntity(request);
        student.setCompany(company);

        return studentRepository.save(student).getId();
    }

    public StudentResponse update(int id, StudentPatchRequest request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        if (request.firstName() != null)
            student.setFirstName(request.firstName());
        if (request.middleName() != null) student.setMiddleName(request.middleName());
        if (request.lastName() != null) student.setLastName(request.lastName());
        if (request.companyId() != null) {
            Company company = companyRepository.findById(request.companyId())
                    .orElseThrow(() -> new EntityNotFoundException("Company not found with id:%d".formatted(request.companyId())));
            student.setCompany(company);
        }
        if (request.email() != null) student.setEmail(request.email());

        Student answer = studentRepository.save(student);
        return studentMapper.toStudentResponse(answer);
    }

    public void delete(Integer id) {
        try{
            studentRepository.deleteById(id);
        } catch (Exception ignored) {

        }

    }

    public List<StudentResponse> getAll(){
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toStudentResponse)
                .toList();
    }
}
