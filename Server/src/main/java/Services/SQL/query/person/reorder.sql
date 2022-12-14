UPDATE lab7_persons AS tt SET
    person_order = (
        WITH lab7_persons AS
        (
            SELECT
                row_number() over (ORDER BY id) AS rn1,
                row_number() over (ORDER BY id DESC) AS rn2,
                *
            FROM lab7_persons WHERE user_id = ?
        )
        SELECT (CASE WHEN tt.person_order = t1.person_order THEN t2.person_order END)
        FROM lab7_persons AS t1
        JOIN lab7_persons AS t2 ON t1.rn1 = t2.rn2
        WHERE (CASE WHEN tt.person_order = t1.person_order THEN t2.person_order END) IS NOT NULL
    )
WHERE user_id = ?

