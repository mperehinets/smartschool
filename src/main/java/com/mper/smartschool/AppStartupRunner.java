package com.mper.smartschool;

import com.mper.smartschool.model.modelsEnum.EntityStatus;
import com.mper.smartschool.model.Pupil;
import com.mper.smartschool.model.SchoolClass;
import com.mper.smartschool.model.modelsEnum.SchoolClassInitial;
import com.mper.smartschool.repository.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Year;

@Component
public class AppStartupRunner implements ApplicationRunner {

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    private final TeacherRepo teacherRepo;

    private final SchoolClassRepo schoolClassRepo;

    private final SubjectRepo subjectRepo;

    private final PupilRepo pupilRepo;

    public AppStartupRunner(UserRepo userRepo, RoleRepo roleRepo, TeacherRepo teacherRepo,
                            SchoolClassRepo schoolClassRepo, SubjectRepo subjectRepo,
                            PupilRepo pupilRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.teacherRepo = teacherRepo;
        this.schoolClassRepo = schoolClassRepo;
        this.subjectRepo = subjectRepo;
        this.pupilRepo = pupilRepo;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Pupil pupil = new Pupil();
        pupil.setId(27L);
        pupil.setFirstName("Maksym");
        pupil.setSecondName("Perehinets");
        pupil.setEmail("mperegine1@gmail.com");
        pupil.setDateBirth(LocalDate.now());
        pupil.setPassword("ViscaElBarca");

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(8L);
        schoolClass.setYear(Year.of(2020));
        schoolClass.setInitial(SchoolClassInitial.C);
        schoolClass.setNumber(5);
        schoolClass.setStatus(EntityStatus.ACTIVE);

        pupil.setSchoolClass(schoolClass);

        pupilRepo.save(pupil);
    }

}
