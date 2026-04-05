package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.StudentPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.StudentRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.StudentResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.StudentRiskResponse;
import com.nb.globalerp.training.sitebackendglobalerp.kafka.dto.EduParticipantCreateDto;
import com.nb.globalerp.training.sitebackendglobalerp.exception.StudentAlreadyExistsException;
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
import java.util.Optional;

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
        if(studentRepository.findByFirstNameAndLastNameAndMiddleNameAndCompany(
                student.getFirstName(),
                student.getLastName(),
                student.getMiddleName(),
                student.getCompany()
        ).isPresent())
        {
            throw new StudentAlreadyExistsException("Обучающийся %s %s %s, работающий в компании %s, уже существует"
                    .formatted(student.getLastName(),
                            student.getFirstName(),
                            student.getMiddleName(),
                            company.getCompanyName()));
        }

        return studentRepository.save(student).getId();
    }

    public int createFromExternalSystem(EduParticipantCreateDto eduParticipantCreateDto) {
        Student student = studentMapper.toStudentEntity(eduParticipantCreateDto);
        student.setEmail("default" + eduParticipantCreateDto.id() + "email@external.ru");
        student.setExternalId(eduParticipantCreateDto.id());
        student.setExternalCode(eduParticipantCreateDto.code());

        Optional<Company> optionalCompany = companyRepository.findByCompanyCode(eduParticipantCreateDto.companyCode());

        Company company = optionalCompany.orElse(
            Company.builder()
                .companyCode(eduParticipantCreateDto.companyCode())
                .companyName(eduParticipantCreateDto.companyName())
                .externalCompanyCode(eduParticipantCreateDto.companyCode())
                .build()
        );
        Company savedCompany = companyRepository.save(company);
        student.setCompany(savedCompany);

        return studentRepository.save(student).getId();
    }

    public StudentResponse update(int id, StudentPatchRequest request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        if (request.firstName() != null) student.setFirstName(request.firstName());
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
        studentRepository.deleteById(id);
    }

    public List<StudentResponse> getAll(){
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toStudentResponse)
                .toList();
    }

    public List<StudentRiskResponse> getStudentsRisk() {
        var projections = studentRepository.getStudentsRisk();
        return studentMapper.toStudentRiskResponseList(projections);
    }
}
