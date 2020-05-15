package com.example.medcenter.service;

import com.example.medcenter.dto.QueuePatientsDTO;
import com.example.medcenter.entity.NewComersEntity;
import com.example.medcenter.entity.QueueEntity;
import com.example.medcenter.entity.UsersEntity;
import com.example.medcenter.repoitory.NewComersRepository;
import com.example.medcenter.repoitory.QueueRepository;
import com.example.medcenter.repoitory.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("registratorService")
public class RegistratorServiceImpl implements RegistratorService {

    @Autowired
    QueueRepository queueRepository;
    @Autowired
    NewComersRepository newComersRepository;
    @Autowired
    UsersRepository usersRepository;

    @Override
    public List<QueuePatientsDTO> getQueuePatientsByDate(Date date) {
        return null;
    }

    @Override
    public List<QueuePatientsDTO> getQueuePatientsForTodayByDoctorId(int doctorId) {
        Date today = new Date();
        List<QueueEntity> queueForToday = queueRepository.findQueueEntitiesByDateAndDoctorId(today,doctorId);
        List<QueuePatientsDTO> queuePatientsList = new ArrayList<>();

        for(QueueEntity queue : queueForToday){
            QueuePatientsDTO queuePatient = new QueuePatientsDTO();
            UsersEntity user = usersRepository.getOne(queue.getUserId());

            queuePatient.setQueueEntity(queue);

            if(queue.getStatus() == 3){
                NewComersEntity newComer = newComersRepository.findNewComersEntityByQueue(queue);
                queuePatient.setName(newComer.getName());
                queuePatient.setSurname(newComer.getSurname());

                //            CanBeCanceled
                Time doctorStartTime = queue.getDoctorFeaturesByDoctorId().getStartTime();
                LocalTime startTime = doctorStartTime.toLocalTime();
                startTime = startTime.plusMinutes(queue.getOrder() * queue.getDoctorFeaturesByDoctorId().getIntervalByIntervalId().getInterval());
                queuePatient.setCanBeCanceled(LocalTime.now().isBefore(startTime));
            }
            if(queue.getStatus() == 1){
                System.out.println(" =============================user ====" + user.getId());
                queuePatient.setName(user.getName());
                queuePatient.setSurname(user.getSurname());
                queuePatient.setCanBeCanceled(false);
            }

            queuePatientsList.add(queuePatient);
        }
        return queuePatientsList;
    }


}
