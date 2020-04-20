package com.mper.smartschool.repository;

import com.mper.smartschool.entity.Schedule;
import com.mper.smartschool.entity.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ScheduleRepo extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s" +
            " where s.teachersSubject.teacher.id = :teacherId and s.date = :date and s.lessonNumber = :lessonNumber")
    Schedule findByTeacherIdAndDateAndLessonNumber(@Param("teacherId") Long teacherId,
                                                   @Param("date") LocalDate date,
                                                   @Param("lessonNumber") Integer lessonNumber);

    Schedule findTop1BySchoolClassOrderByDateDesc(SchoolClass schoolClass);
}
