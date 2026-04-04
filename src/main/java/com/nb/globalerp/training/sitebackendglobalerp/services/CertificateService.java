package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CreateCertificateResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.GetStudentCertificateResponse;
import com.nb.globalerp.training.sitebackendglobalerp.config.cert.CertificationProperty;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Course;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.GroupMember;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Student;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.GroupMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.Instant;

@Service
public class CertificateService {

    private final GroupMemberRepository groupMemberRepository;

    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;

    private byte[] pdfCertBytes;
    private File fontFile;

    private final CertificationProperty certificationProperty;

    public CertificateService(
        GridFsTemplate gridFsTemplate,
        GridFsOperations gridFsOperations,
        GroupMemberRepository groupMemberRepository,
        CertificationProperty certificationProperty
    ) {
        this.groupMemberRepository = groupMemberRepository;
        this.gridFsTemplate = gridFsTemplate;
        this.gridFsOperations = gridFsOperations;
        this.certificationProperty = certificationProperty;

        try (var inputStream = new FileInputStream("src/main/resources/cerificate/certificate_template.pdf");) {
            this.pdfCertBytes = inputStream.readAllBytes();
            this.fontFile = new File("src/main/resources/cerificate/arial.ttf");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public GetStudentCertificateResponse getCertificate(Integer studentId, Integer groupId, String certificateId) {
        if (certificateId != null) {
            return getCertificate(certificateId);
        }
        return getCertificate(studentId, groupId);
    }

    public GetStudentCertificateResponse getCertificate(int studentId, int groupId) {
        GroupMember groupMember = groupMemberRepository.findByStudentIdAndGroupId(studentId, groupId)
            .orElseThrow(() -> new EntityNotFoundException(
                String.format("GroupMember not found with studentId: %s and groupId: %s", studentId, groupId)
            ));

        return getCertificate(groupMember.getCertificateId());
    }

    public GetStudentCertificateResponse getCertificate(String certificateId) {
        try {
            Query query = new Query().addCriteria(Criteria.where("_id").is(certificateId));
            GridFSFile file = gridFsTemplate.findOne(query);
            GridFsResource resource = gridFsOperations.getResource(file);
            return new GetStudentCertificateResponse(
                file.getFilename(),
                file.getLength(),
                resource.getInputStream().readAllBytes()
            );
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Transactional
    public CreateCertificateResponse createCertificateForStudent(int studentId, int groupId) {
        GroupMember groupMember = groupMemberRepository.findByStudentIdAndGroupId(studentId, groupId)
            .orElseThrow(() -> new EntityNotFoundException(
                String.format("GroupMember not found with studentId: %s and groupId: %s", studentId, groupId)
            ));

        Student student = groupMember.getStudent();
        Course course = groupMember.getGroup().getCourse();

        String fio = "%s %s %s".formatted(student.getFirstName(), student.getMiddleName(), student.getLastName());
        String courseName = course.getName() + ": " + course.getDescription();

        byte[] newCert = generateNewCertificate(fio, courseName, pdfCertBytes);

        DBObject metadata = new BasicDBObject();
        metadata.put("type", "pdf");

        String fileName = String.format(certificationProperty.filenameTemplate(), fio.replaceAll("\\s", "_"), Instant.now());
        ObjectId fileId = gridFsTemplate.store(new ByteArrayInputStream(newCert), fileName, "application/pdf", metadata);

        groupMember.setCertificateId(fileId.toHexString());
        return new CreateCertificateResponse(fileId.toHexString());
    }

    private byte[] generateNewCertificate(String fio, String courseDesc, byte[] templatePdfCert) {
        try {
            PDDocument document = Loader.loadPDF(templatePdfCert);
            PDPage page = document.getPage(0);

            PDPageContentStream content = new PDPageContentStream(
                document,
                page,
                PDPageContentStream.AppendMode.APPEND,
                true
            );

            PDFont font = PDType0Font.load(document, fontFile);

            CertificationProperty.Position position = certificationProperty.position();

            content.beginText();
            content.setFont(font, 15);
            content.newLineAtOffset(position.course().x(), position.course().y());
            content.showText(courseDesc);
            content.endText();

            content.beginText();
            content.setFont(font, 18);
            content.newLineAtOffset(position.fio().x(), position.fio().y());
            content.showText(fio);
            content.endText();

            content.close();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            document.close();

            return baos.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
