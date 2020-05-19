package com.example.medcenter.service;

import com.example.medcenter.dto.DoctorDTO;
import com.example.medcenter.dto.DoctorsPatientDTO;
import com.example.medcenter.dto.TimeDTO;
import com.example.medcenter.dto.TimetableDTO;
import com.example.medcenter.entity.*;
import com.example.medcenter.repoitory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class DoctorsServiceImpl implements DoctorsService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    DoctorsFeaturesRepository doctorsFeaturesRepository;
    @Autowired
    IntervalRepository intervalRepository;
    @Autowired
    QueueRepository queueRepository;
    @Autowired
    DoctorsTypeRepository doctorsTypeRepository;
    @Autowired
    public JavaMailSender emailSender;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<DoctorDTO> getDoctors() {
        return null;
    }

    @Override
    public DoctorDTO getDoctorById(long id) {
        return null;
    }

    @Override
    public DoctorDTO getDoctorByType(String type) {
        return null;
    }

    @Override
    public void saveDoctor(DoctorDTO doctorDTO) {

    }

    @Override
    public void saveDefaultDoctor(UsersEntity user , int doctorTypeId) {
        UsersEntity savedUser = usersRepository.findUsersEntityByUsername(user.getUsername());
        IntervalEntity interval = intervalRepository.getOne(3);

        DoctorsFeaturesEntity doctor = new DoctorsFeaturesEntity();
        doctor.setUsersByDoctorId(savedUser);
        doctor.setIntervalByIntervalId(interval);
        doctor.setDoctorsTypeEntities(new HashSet());
        doctor.setInfo("");
        doctor.setStartTime(new Time(9,0,0));
        doctor.setEndTime(new Time(17,0,0));

        Set<DoctorsTypeEntity> doctorType = new HashSet<>();
        doctorType.add(doctorsTypeRepository.getOne(doctorTypeId));
        doctor.setDoctorsTypeEntities(doctorType);

        doctorsFeaturesRepository.save(doctor);
    }

    @Override
    public void updateDoctor(DoctorDTO doctorDTO) {

    }

    @Override
    public List<TimeDTO> getTimetableByDoctorFeaturesIdAndDate(int doctorId , Date date) {
        DoctorsFeaturesEntity doctorsFeatures = doctorsFeaturesRepository.getDoctorsFeaturesEntityById(doctorId);
        LocalTime lunchTimeStart = LocalTime.parse("12:00:00");
        LocalTime lunchTimeEnd = LocalTime.parse("13:00:00");
        LocalTime saturdayEndTime = LocalTime.parse("15:00:00");

        LocalTime startTime = doctorsFeatures.getStartTime().toLocalTime();
        LocalTime endTime = doctorsFeatures.getEndTime().toLocalTime();
        LocalTime localTime = startTime;
        List<TimeDTO> timeList = new ArrayList<>();

        List<LocalTime> possibleTimes = new ArrayList<>();
        int interval = doctorsFeatures.getIntervalByIntervalId().getInterval();
        while(0 <= (endTime.compareTo(localTime))){
            if(0 >= (lunchTimeStart.compareTo(localTime.plusMinutes(interval))) && 0 <= (lunchTimeEnd.compareTo(localTime.plusMinutes(interval)))){
                System.out.println(localTime+" - " +localTime.plusMinutes(interval));
                possibleTimes.add(localTime);
                possibleTimes.add(lunchTimeStart);
                localTime = lunchTimeEnd;
            }
            else{
                possibleTimes.add(localTime);
                localTime = localTime.plusMinutes(interval);
            }
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        int order = 1;
        for(int i=0; i<possibleTimes.size()-1 ; i++){
//            if(0 <= (lunchTimeStart.compareTo(possibleTimes.get(i).plusMinutes(interval)))  || 0 >= (lunchTimeEnd.compareTo(possibleTimes.get(i).plusMinutes(interval))) ) {
            if(0 != lunchTimeStart.compareTo(possibleTimes.get(i+1))) {
                TimeDTO timeDTO = new TimeDTO();
                timeDTO.setOrder(order);
                timeDTO.setTime(possibleTimes.get(i) + " - " + possibleTimes.get(i + 1));
                if (0 > (lunchTimeStart.compareTo(possibleTimes.get(i + 1))) && 0 < (lunchTimeEnd.compareTo(possibleTimes.get(i)))) {
                    timeDTO.setFree(false);
                    timeDTO.setStatus(4);
                }
                else {
                    if(dayOfWeek == 7 &&  0 > (saturdayEndTime.compareTo(possibleTimes.get(i + 1)))){
                        timeDTO.setFree(false);
                        timeDTO.setStatus(4);
                    }else {
                        timeDTO.setFree(true);
                        timeDTO.setStatus(0);
                    }
                }
                order++;
                timeList.add(timeDTO);
            }else{
                if(0 == lunchTimeStart.compareTo(possibleTimes.get(i).plusMinutes(interval))){
                    TimeDTO timeDTO = new TimeDTO();
                    timeDTO.setOrder(order);
                    timeDTO.setTime(possibleTimes.get(i) + " - " + possibleTimes.get(i + 1));
                    timeDTO.setFree(true);
                    timeDTO.setStatus(0);
                    order++;
                    timeList.add(timeDTO);
                }
            }
        }

        List<QueueEntity> queueEntities = queueRepository.findQueueEntitiesByDateAndDoctorId(date,doctorId);
        for(TimeDTO time : timeList){
            for(QueueEntity queue : queueEntities){
//                if (queueEntity.getIntervalId() == doctorsFeatures.getIntervalId()){}
                if (time.getOrder() == queue.getOrder()){
                    if(queue.getStatus() > 0){
                        time.setFree(false);
                        time.setStatus(queue.getStatus());
                    }
                }
            }
        }

        return timeList;
    }


    @Override
    public List<TimetableDTO> getTimetableByDoctorFeaturesId(int doctorId) {
        List<Date> dateList = new ArrayList<>();
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        for(int i=0 ; i<7 ; i++) {
            if (calendar.get(Calendar.DAY_OF_WEEK) != 1) {
                Date d = calendar.getTime();
                dateList.add(d);
            }
            calendar.add(Calendar.DATE, 1);
        }

        int id = 1;
        List<TimetableDTO> timetables = new ArrayList<>();
        for (Date date : dateList){
            TimetableDTO timetableDTO = new TimetableDTO();
            timetableDTO.setId(id);
            timetableDTO.setDate(date);
            timetableDTO.setDoctorId(doctorId);
            timetableDTO.setTimeList(getTimetableByDoctorFeaturesIdAndDate(doctorId,date));
            timetables.add(timetableDTO);
            id++;
        }

        return timetables;
    }

    @Override
    public List<DoctorsPatientDTO>  getPatientListByDoctorIdAndDate(int doctorId, Date date) {

        List<QueueEntity> queueEntityList = queueRepository.findQueueEntitiesByDateAndDoctorId(date , doctorId);
        List<UsersEntity> patientsList = new ArrayList<>();
        List<DoctorsPatientDTO> patientDtoList = new ArrayList<>();
        int i = 1;
        for(QueueEntity queue : queueEntityList){
            if(queue.getStatus()!=0 && queue.getStatus()!=2) {
                DoctorsPatientDTO patientDTO = new DoctorsPatientDTO();
                UsersEntity patient = usersRepository.getOne(queue.getUserId());
                patientDTO.setId(i);
                patientDTO.setPatient(patient);
                patientDTO.setQueue(queue);


                patientDtoList.add(patientDTO);
                i++;
            }
        }
        return patientDtoList;
    }

    @Override
    public List<DoctorsPatientDTO>  getTodayPatientListByDoctorId(int doctorId) {
        Date today = new Date();
        return getPatientListByDoctorIdAndDate(doctorId , today);
    }


    @Override
    public void changeDoctorWorkingCredentials(int doctorId) {
        List<QueueEntity> queueEntities = queueRepository.findQueueEntitiesByDateAfterAndDoctorId(new Date(), doctorId);
        System.out.println(queueEntities.size());
        String subject = "Ваша запись на прием отменяется.";
        String  text = "В связи с тем что Врач на которого вы записались, изменил время работы, Ваша запись на прием отменяется." +
                " Пожалуйста запишитесь на прием повторно в удобное время.";
        for(QueueEntity q : queueEntities){
            UsersEntity patient = usersRepository.getOne(q.getUserId());
            sendEmail(patient.getEmail(), subject, text);
            queueRepository.delete(q);
        }
    }

    public void sendEmail(String to ,String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }



}
