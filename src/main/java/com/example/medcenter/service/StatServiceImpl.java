package com.example.medcenter.service;

import com.example.medcenter.entity.DoctorsFeaturesEntity;
import com.example.medcenter.entity.QueueEntity;
import com.example.medcenter.repoitory.*;
import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("statService")
public class StatServiceImpl implements StatService {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    DoctorsFeaturesRepository doctorsFeaturesRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UsersDetailsService usersDetailsService;
    @Autowired
    DoctorsService doctorsService;
    @Autowired
    DoctorsTypeRepository doctorsTypeRepository;
    @Autowired
    QueueRepository queueRepository;


    @Override
    public List<List<Object>> getNumberOfVisitsByDoctors() {
        List<List<Object>> stat = new ArrayList<>();
        List<DoctorsFeaturesEntity> doctors = doctorsFeaturesRepository.findAll();

        for(DoctorsFeaturesEntity doctor : doctors){
            List<Object> list = new ArrayList<>();
            list.add(doctor.getDoctorTypeString());
            list.add(queueRepository.findQueueEntitiesByDoctorId(doctor.getId()).size());

            stat.add(list);
        }

        return stat;
    }

    @Override
    public List<List<Object>> getNumberOfVisitsByMonths() {
        List<List<Object>> stat = new ArrayList<>();
        List<Integer> months = new ArrayList();
        String[] monthStr = new String[]{"Янв","Фев","Март","Апр","Май","Июнь","Июль","Авг","Сен","Окт","Ноя","Дек"};

        LocalDate now = LocalDate.now();
        LocalDate sixMonthEarlier = now.minusMonths(4);
        sixMonthEarlier = sixMonthEarlier.minusDays(sixMonthEarlier.getDayOfMonth());
        System.out.println(sixMonthEarlier);
        LocalDate localDate = sixMonthEarlier;
        for(int i=0 ; i<6 ; i++){
            List l = new ArrayList();
            l.add(localDate.plusMonths(i));

            switch (localDate.plusMonths(i).getMonthValue()){
                case 1:
                    l.add(monthStr[0]);
                    break;
                case 2:
                    l.add(monthStr[1]);
                    break;
                case 3:
                    l.add(monthStr[2]);
                    break;
                case 4:
                    l.add(monthStr[3]);
                    break;
                case 5:
                    l.add(monthStr[4]);
                    break;
                case 6:
                    l.add(monthStr[5]);
                    break;
                case 7:
                    l.add(monthStr[6]);
                    break;
                case 8:
                    l.add(monthStr[7]);
                    break;
                case 9:
                    l.add(monthStr[8]);
                    break;
                case 10:
                    l.add(monthStr[9]);
                    break;
                case 11:
                    l.add(monthStr[10]);
                    break;
                case 12:
                    l.add(monthStr[11]);
                    break;
            }
            Date startDate = java.sql.Date.valueOf(localDate.plusMonths(i));
            Date endDate = java.sql.Date.valueOf(localDate.plusMonths(i+1));
            List<QueueEntity> queueForMonth = queueRepository.findQueueEntitiesByDateBetween(startDate,endDate);
            l.add(queueForMonth.size());
            stat.add(l);
        }

        return stat;
    }
}
