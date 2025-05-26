package com.example.nursing_handover_app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//DB 관리 클래스입니다.
class AppDatabaseHelper (context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null, DATABASE_VERSION){
    companion object {
        private const val DATABASE_NAME = "nursing_handover.db"
        private const val DATABASE_VERSION = 2 //1 -> 2
        private const val TABLE_WORKPLACE = "workplace"
        private const val TABLE_USERS = "users"
        private const val TABLE_DEPT = "dept"
        private const val TABLE_SCHEDULE = "schedule"
    }

    override fun onCreate(db: SQLiteDatabase) {
        //근무지 테이블 -> 로그인①
        val createTable = """
            CREATE TABLE $TABLE_WORKPLACE (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL
                
            );
        """.trimIndent()
        db.execSQL(createTable)

        //진료과 테이블 -> 로그인②
        val createDeptTable = """
            CREATE TABLE $TABLE_DEPT (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL
            );
        """.trimIndent()
        db.execSQL(createDeptTable)

        //이용자 테이블 -> 로그인③ + 근무자 리스트
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                workplace TEXT NOT NULL,
                dept TEXT NOT NULL,
                position TEXT NOT NULL,
                user_id TEXT NOT NULL,
                password TEXT NOT NULL,
                image TEXT,
                phone TEXT
            );
        """.trimIndent()
        db.execSQL(createUsersTable)

        //스케줄 테이블 -> 근무자 상세 페이지 + 메인화면 캘린더
        val createScheduleTable = """
            CREATE TABLE $TABLE_SCHEDULE (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                worker_id INTEGER NOT NULL,
                work_date TEXT NOT NULL,
                shift_type TEXT NOT NULL
            );
        """.trimIndent()
        db.execSQL(createScheduleTable)

        val hospitals = listOf(
            "서울아산병원",
            "삼성서울병원",
            "서울대학교병원",
            "세브란스병원",
            "서울성모병원"
        )
        //근무지 값
        for (hospital in hospitals) {
            db.execSQL("INSERT INTO $TABLE_WORKPLACE (name) VALUES ('$hospital')")
        }

        val departments = listOf(
            "내과",
            "산부인과",
            "신경외과",
            "외상외과",
            "응급의학과"
        )
        //진료과 값
        for (department in departments) {
            db.execSQL("INSERT INTO $TABLE_DEPT (name) VALUES ('$department')")
        }

        //이용자 값
        db.execSQL("INSERT INTO $TABLE_USERS (name, workplace, dept, position, user_id, password, image, phone) VALUES ('홍길동', '서울아산병원', '내과', '수간호사', 'hgd', 'gd123', 'hong', '010-3453-4786')")
        db.execSQL("INSERT INTO $TABLE_USERS (name, workplace, dept, position, user_id, password, image, phone) VALUES ('임꺽정', '서울아산병원', '내과', '책임간호사', 'igj', 'gj123', 'im', '010-1932-3461')")
        db.execSQL("INSERT INTO $TABLE_USERS (name, workplace, dept, position, user_id, password, image, phone) VALUES ('장길산', '서울아산병원', '내과', '주임간호사', 'jks', 'ks123', 'jang', '010-2435-5593')")
        db.execSQL("INSERT INTO $TABLE_USERS (name, workplace, dept, position, user_id, password, image, phone) VALUES ('김홍도', '서울아산병원', '내과', '간호사', 'khd', 'hd123', 'kim', '010-9453-9834')")
        db.execSQL("INSERT INTO $TABLE_USERS (name, workplace, dept, position, user_id, password, image, phone) VALUES ('정약용', '서울아산병원', '내과', '간호사', 'jyy', 'yy123', 'jeong', '010-8932-4582')")

        //스케줄 값 -> 홍길동과 임꺽정이 무조건 교대가 되도록 생성
        //홍길동(worker_id=1)
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-01', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-02', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-03', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-04', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-05', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-06', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-07', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-08', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-09', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-10', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-11', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-12', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-13', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-14', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-15', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-16', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-17', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-18', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-19', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-20', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-21', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-22', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-23', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-24', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-25', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-26', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-27', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-28', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-29', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-30', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (1, '2025-05-31', 'Eve')")

        //임꺽정(worker_id=2)
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-01', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-02', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-03', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-04', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-05', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-06', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-07', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-08', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-09', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-10', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-11', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-12', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-13', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-14', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-15', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-16', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-17', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-18', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-19', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-20', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-21', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-22', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-23', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-24', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-25', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-26', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-27', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-28', 'Nig')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-29', 'Day')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-30', 'Eve')")
        db.execSQL("INSERT INTO $TABLE_SCHEDULE (worker_id, work_date, shift_type) VALUES (2, '2025-05-31', 'Nig')")

        // 인계기록 테이블 생성
        val createHandoverTable = """
            CREATE TABLE handover (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                hand_nurse TEXT,
                take_nurse TEXT,
                write_date TEXT,
                room_num INTEGER,
                bed_num TEXT,
                name TEXT,
                sex TEXT,
                age INTEGER,
                visitDate TEXT,
                hospitalRouteGroup TEXT,
                historyGroup TEXT,
                historyInput TEXT,
                allergyGroup TEXT,
                allergyInput TEXT,
                surgeryGroup TEXT,
                surgeryInput TEXT,
                cause TEXT,
                blood1 INTEGER,
                blood2 INTEGER,
                pulse INTEGER,
                breath INTEGER,
                temperature INTEGER,
                oxy INTEGER,
                SpO2 INTEGER,
                pee INTEGER,
                defacateGroup TEXT,
                defacateInput TEXT,
                pipe INTEGER,
                pipe_amount INTEGER,
                color TEXT
            );
        """.trimIndent()
        db.execSQL(createHandoverTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL("DROP TABLE IF EXISTS $TABLE_WORKPLACE")
//        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
//        db.execSQL("DROP TABLE IF EXISTS $TABLE_DEPT")
//        db.execSQL("DROP TABLE IF EXISTS $TABLE_SCHEDULE")
//        onCreate(db)
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE users ADD COLUMN phone TEXT")
            db.execSQL("ALTER TABLE users ADD COLUMN image TEXT")
        }
    }
    //인수인계 연동
    fun insertHandover(
        hand_nurse: String,
        take_nurse: String,
        write_date: String,
        room_num: Int,
        bed_num: String,
        name: String,
        sex: String,
        age: Int,
        visitDate: String,
        hospitalRouteGroup: String,
        historyGroup: String,
        historyInput: String,
        allergyGroup: String,
        allergyInput: String,
        surgeryGroup: String,
        surgeryInput: String,
        cause: String,
        blood1: Int,
        blood2: Int,
        pulse: Int,
        breath: Int,
        temperature: Int,
        oxy: Int,
        SpO2: Int,
        pee: Int,
        defacateGroup: String,
        defacateInput: String,
        pipe: Int,
        pipe_amount: Int,
        color: String
    ) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("hand_nurse", hand_nurse)
            put("take_nurse", take_nurse)
            put("write_date", write_date)
            put("room_num", room_num)
            put("bed_num", bed_num)
            put("name", name)
            put("sex", sex)
            put("age", age)
            put("visitDate", visitDate)
            put("hospitalRouteGroup", hospitalRouteGroup)
            put("historyGroup", historyGroup)
            put("historyInput", historyInput)
            put("allergyGroup", allergyGroup)
            put("allergyInput", allergyInput)
            put("surgeryGroup", surgeryGroup)
            put("surgeryInput", surgeryInput)
            put("cause", cause)
            put("blood1", blood1)
            put("blood2", blood2)
            put("pulse", pulse)
            put("breath", breath)
            put("temperature", temperature)
            put("oxy", oxy)
            put("SpO2", SpO2)
            put("pee", pee)
            put("defacateGroup", defacateGroup)
            put("defacateInput", defacateInput)
            put("pipe", pipe)
            put("pipe_amount", pipe_amount)
            put("color", color)
        }
        db.insert("handover", null, values)
        db.close()
    }
    fun getScheduleByUserId(userId: Int): String {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT work_date, shift_type FROM schedule WHERE worker_id = ? ORDER BY work_date",
            arrayOf(userId.toString())
        )
        val sb = StringBuilder()
        while (cursor.moveToNext()) {
            val date = cursor.getString(0)
            val shift = cursor.getString(1)
            sb.append("$date : $shift\n")
        }
        cursor.close()
        return if (sb.isNotEmpty()) sb.toString() else "스케줄이 없습니다."
    }
}