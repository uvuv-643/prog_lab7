UPDATE persons as tt SET
    person_order = (
        with persons as
        (
            select
                row_number() over (order by id) as rn1,
                row_number() over (order by id desc) as rn2,
                *
            from persons where user_id = ?
        )
        select (case when tt.person_order = t1.person_order then t2.person_order end)
        from persons as t1
        join persons as t2 on t1.rn1 = t2.rn2
        WHERE (case when tt.person_order = t1.person_order then t2.person_order end) is not null
    )
WHERE user_id = ?

