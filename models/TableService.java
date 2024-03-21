package models;

import presenters.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class TableService implements Model {

    private Collection<Table> tables;

    @Override
    public Collection<Table> loadTables() {
        if (tables == null) {
            tables = new ArrayList<>();

            tables.add(new Table());
            tables.add(new Table());
            tables.add(new Table());
            tables.add(new Table());
            tables.add(new Table());
        }

        return tables;
    }

    @Override
    public int reservationTable(Date reservationDate, int tableNo, String name) {
        for (Table table : tables) {
            if (table.getNo() == tableNo) {
                Reservation reservation = new Reservation(table, reservationDate, name);
                table.getReservations().add(reservation);
                return reservation.getId();
            }
        }
        throw new RuntimeException("Некорректный номер столика");
    }

    @Override
    public int changeReservationTable(int oldReservation, Date newReservationDate, int newTableNo, String name) {
        Reservation reservationToChange = null;
        Table oldTable = null;
    
        // Находим бронирование по его ID и столик, на котором оно было сделано
        for (Table table : tables) {
            for (Reservation reservation : table.getReservations()) {
                if (reservation.getId() == oldReservation) {
                    reservationToChange = reservation;
                    oldTable = table;
                    break;
                }
            }
            if (reservationToChange != null) {
                break;
            }
        }
    
        if (reservationToChange == null) {
            throw new RuntimeException("Бронирование не найдено");
        }
        
        Table newTable = null;
        for (Table table : tables) {
            if (table.getNo() == newTableNo) {
                newTable = table;
                break;
            }
        }
    
        if (newTable == null) {
            throw new RuntimeException("Столик не найден");
        }
        
        Reservation newReservation = new Reservation(newTable, newReservationDate, name);
        newTable.getReservations().add(newReservation);
    
        return newReservation.getId();
        
    }
}
