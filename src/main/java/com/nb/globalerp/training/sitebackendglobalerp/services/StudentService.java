package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.StudentPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.StudentRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.StudentResponse;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.StudentMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Company;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Student;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentResponse findById(int id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
        return studentMapper.toResponse(student);
    }

    public int create(StudentRequest request) {
        Company company = new Company();
        company.setId(request.companyId());

        Student student = studentMapper.toEntity(request);
        student.setCompany(company);

        return studentRepository.save(student).getId();
    }

    public StudentResponse update(int id, StudentPatchRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        if (request.firstName() != null) student.setFirstName(request.firstName());
        if (request.middleName() != null) student.setMiddleName(request.middleName());
        if (request.lastName() != null) student.setLastName(request.lastName());
        if (request.companyId() != null) {
            Company company = new Company();
            company.setId(request.companyId());
            student.setCompany(company);
        }
        if (request.email() != null) student.setEmail(request.email());

        return studentMapper.toResponse(studentRepository.save(student));
    }

    public void delete(int id) {
        studentRepository.deleteById(id);
    }
}
