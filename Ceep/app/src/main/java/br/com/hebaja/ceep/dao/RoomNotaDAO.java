package br.com.hebaja.ceep.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.hebaja.ceep.model.Nota;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomNotaDAO {

    @Insert(onConflict = REPLACE)
    long salva(Nota nota);

    @Query("SELECT * FROM nota")
    List<Nota> todos();

    @Delete
    void remove(Nota nota);

    @Update
    void altera(Nota nota);
}
