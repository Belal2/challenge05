INSERT INTO
    legal.role_test (id, created_by, created_date, "name")
VALUES
    (1, NULL, NULL, 'ADMIN')
ON CONFLICT (id) DO NOTHING;

INSERT INTO
    legal.role_test (id, created_by, created_date, "name")
VALUES
    (2, NULL, NULL, 'USER')
ON CONFLICT (id) DO NOTHING;