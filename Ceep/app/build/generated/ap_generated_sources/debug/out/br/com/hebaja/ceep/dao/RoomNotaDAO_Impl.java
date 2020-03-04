package br.com.hebaja.ceep.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import br.com.hebaja.ceep.model.Nota;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class RoomNotaDAO_Impl implements RoomNotaDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Nota> __insertionAdapterOfNota;

  private final EntityDeletionOrUpdateAdapter<Nota> __deletionAdapterOfNota;

  private final EntityDeletionOrUpdateAdapter<Nota> __updateAdapterOfNota;

  public RoomNotaDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNota = new EntityInsertionAdapter<Nota>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Nota` (`id`,`titulo`,`descricao`,`color`,`posicao`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Nota value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getTitulo() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitulo());
        }
        if (value.getDescricao() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescricao());
        }
        stmt.bindLong(4, value.getColor());
        stmt.bindLong(5, value.getPosicao());
      }
    };
    this.__deletionAdapterOfNota = new EntityDeletionOrUpdateAdapter<Nota>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Nota` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Nota value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
      }
    };
    this.__updateAdapterOfNota = new EntityDeletionOrUpdateAdapter<Nota>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Nota` SET `id` = ?,`titulo` = ?,`descricao` = ?,`color` = ?,`posicao` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Nota value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getTitulo() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitulo());
        }
        if (value.getDescricao() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescricao());
        }
        stmt.bindLong(4, value.getColor());
        stmt.bindLong(5, value.getPosicao());
        if (value.getId() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, value.getId());
        }
      }
    };
  }

  @Override
  public long salva(final Nota nota) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfNota.insertAndReturnId(nota);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void remove(final Nota nota) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfNota.handle(nota);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void altera(final Nota nota) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfNota.handle(nota);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Nota> todos() {
    final String _sql = "SELECT * FROM nota";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitulo = CursorUtil.getColumnIndexOrThrow(_cursor, "titulo");
      final int _cursorIndexOfDescricao = CursorUtil.getColumnIndexOrThrow(_cursor, "descricao");
      final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
      final int _cursorIndexOfPosicao = CursorUtil.getColumnIndexOrThrow(_cursor, "posicao");
      final List<Nota> _result = new ArrayList<Nota>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Nota _item;
        _item = new Nota();
        final Long _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getLong(_cursorIndexOfId);
        }
        _item.setId(_tmpId);
        final String _tmpTitulo;
        _tmpTitulo = _cursor.getString(_cursorIndexOfTitulo);
        _item.setTitulo(_tmpTitulo);
        final String _tmpDescricao;
        _tmpDescricao = _cursor.getString(_cursorIndexOfDescricao);
        _item.setDescricao(_tmpDescricao);
        final int _tmpColor;
        _tmpColor = _cursor.getInt(_cursorIndexOfColor);
        _item.setColor(_tmpColor);
        final int _tmpPosicao;
        _tmpPosicao = _cursor.getInt(_cursorIndexOfPosicao);
        _item.setPosicao(_tmpPosicao);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
