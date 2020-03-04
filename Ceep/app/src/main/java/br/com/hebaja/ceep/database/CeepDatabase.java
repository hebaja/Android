package br.com.hebaja.ceep.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import br.com.hebaja.ceep.dao.RoomNotaDAO;
import br.com.hebaja.ceep.model.Nota;

@Database(entities = Nota.class, version = 1, exportSchema = false)
public abstract class CeepDatabase extends RoomDatabase {

    public abstract RoomNotaDAO getRoomNotaDao();

}
