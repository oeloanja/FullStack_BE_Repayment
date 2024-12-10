CREATE TABLE IF NOT EXISTS repayment (
    repayment_id SERIAL PRIMARY KEY,
    group_id INT NOT NULL,
    loan_id INT NOT NULL,
    repayment_principal DECIMAL(15, 2) NOT NULL,
    repayment_interest DECIMAL(15, 2) NOT NULL,
    due_date INT NOT NULL,
    term INT NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS repayment_success (
    repayment_success_id SERIAL PRIMARY KEY,
    repayment_id INT PRIMARY KEY,
    payment_date TIMESTAMP NOT NULL,
    repayment_times INT NOT NULL,
    created_at TIMESTAMP NOT NULL
    FOREIGN KEY (repayment_id) REFERENCES repayment(repayment_id)
);

CREATE TABLE IF NOT EXISTS repayment_fail (
    repayment_fail_id SERIAL PRIMARY KEY,
    repayment_id INT PRIMARY KEY,
    payment_date TIMESTAMP NOT NULL,
    repayment_left DECIMAL(15, 2) NOT NULL,
    repayment_times INT NOT NULL,
    created_at TIMESTAMP NOT NULL
    FOREIGN KEY (repayment_id) REFERENCES repayment(repayment_id)
);