package com.mper.smartschool.repository;

import com.mper.smartschool.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

public interface ScheduleRepo extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s " +
            "where s.date between :startDate " +
            "and :endDate " +
            "and s.teachersSubject.teacher.id = :teacherId " +
            "and s.lessonNumber = :lessonNumber " +
            "and (dayofweek(s.date)) = :dayOfWeek")
    Collection<Schedule> findByStartDateAndEndDateAndTeacherIdAndLessonNumberAndDayOfWeek(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("teacherId") Long teacherId,
            @Param("lessonNumber") Integer lessonNumber,
            @Param("dayOfWeek") Integer dayOfWeek);

    // Find last schedule by schoolClassId
    Schedule findFirstBySchoolClassIdOrderByDateDesc(Long id);

    default Schedule findLastByClassId(Long id) {
        return findFirstBySchoolClassIdOrderByDateDesc(id);
    }

    Collection<Schedule> findBySchoolClassIdAndDate(Long schoolClassId, LocalDate date);

    Schedule findByTeachersSubjectTeacherIdAndDateAndLessonNumber(Long teacherId, LocalDate date, Integer lessonNumber);

    default Schedule findByTeacherIdAndDateAndLessonNumber(Long teacherId, LocalDate date, Integer lessonNumber) {
        return findByTeachersSubjectTeacherIdAndDateAndLessonNumber(teacherId, date, lessonNumber);
    }

    @Modifying
    @Transactional
    void deleteByDateAfterAndSchoolClassId(LocalDate date, Long schoolClassId);
}
