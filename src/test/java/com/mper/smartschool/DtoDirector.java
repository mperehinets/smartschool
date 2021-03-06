package com.mper.smartschool;

import com.mper.smartschool.dto.*;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.entity.modelsEnum.PupilsLessonStatus;
import com.mper.smartschool.entity.modelsEnum.SchoolClassInitial;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;

public final class DtoDirector {

    private DtoDirector() {
    }

    public static UserDto makeTestUserDtoById(Long id) {
        return UserDto.userBuilder()
                .id(id)
                .firstName("User")
                .secondName("User")
                .dateBirth(LocalDate.of(2000, 12, 17))
                .email("user" + id + "@gmail.com")
                .avatarName(null)
                .roles(new HashSet<>())
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static TeacherDto makeTestTeacherDtoById(Long id) {
        return TeacherDto.teacherBuilder()
                .id(id)
                .firstName("Teacher")
                .secondName("Teacher")
                .dateBirth(LocalDate.of(2000, 12, 19))
                .email("teacher" + id + "@gmail.com")
                .education("Teacher")
                .avatarName(null)
                .roles(null)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static PupilDto makeTestPupilDtoById(Long id) {
        return PupilDto.pupilBuilder()
                .id(id)
                .firstName("Pupil")
                .secondName("Pupil")
                .dateBirth(LocalDate.of(2000, 12, 17))
                .email("pupil" + id + "@gmail.com")
                .password("pupilPassword1")
                .schoolClass(null)
                .signedPersons(new HashSet<>())
                .avatarName(null)
                .roles(null)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static SchoolClassDto makeTestSchoolClassDtoById(Long id) {
        return SchoolClassDto.builder()
                .id(id)
                .number(new Random().nextInt(11) + 1)
                .initial(SchoolClassInitial.A)
                .classTeacher(makeTestTeacherDtoById(id))
                .build();
    }

    public static SubjectDto makeTestSubjectDtoById(Long id) {
        return SubjectDto.builder()
                .id(id)
                .name("Subject")
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static TeachersSubjectDto makeTestTeachersSubjectDtoById(Long id) {
        return TeachersSubjectDto.builder()
                .id(id)
                .subject(makeTestSubjectDtoById(id))
                .teacher(makeTestTeacherDtoById(id))
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static SignedPersonDto makeTestSignedPersonDtoById(Long id) {
        return SignedPersonDto.builder()
                .id(id)
                .fullName("Signed Person")
                .email("signedPerson" + id + "@gmail.com")
                .build();
    }

    public static TemplateScheduleDto makeTestTemplateScheduleDtoById(Long id) {
        return TemplateScheduleDto.builder()
                .id(id)
                .classNumber(new Random().nextInt(11) + 1)
                .dayOfWeek(DayOfWeek.FRIDAY)
                .lessonNumber(new Random().nextInt(7) + 1)
                .teachersSubject(TeachersSubjectDto.builder()
                        .subject(makeTestSubjectDtoById(id))
                        .build())
                .build();
    }

    public static ScheduleDto makeTestScheduleDtoById(Long id) {
        return ScheduleDto.builder()
                .id(id)
                .lessonNumber(new Random().nextInt(7) + 1)
                .date(LocalDate.now().plusDays(id))
                .schoolClass(makeTestSchoolClassDtoById(id))
                .teachersSubject(makeTestTeachersSubjectDtoById(id))
                .build();
    }

    public static PupilSuccessDto makeTestPupilSuccessDtoById(Long id) {
        return PupilSuccessDto.builder()
                .id(id)
                .pupil(makeTestPupilDtoById(id))
                .schedule(makeTestScheduleDtoById(id + 1))
                .pupilsLessonStatus(PupilsLessonStatus.PRESENT)
                .rating(new Random().nextInt(12) + 1)
                .build();
    }

    public static HomeworkDto makeTestHomeworkDtoById(Long id) {
        return HomeworkDto.builder()
                .id(id)
                .schedule(makeTestScheduleDtoById(id))
                .homework("Homework")
                .build();
    }
}
