INSERT INTO account (id, account_type)
VALUES ('acc-001', 'STANDARD');

INSERT INTO account (id, account_type)
VALUES ('acc-002', 'PREMIUM');

INSERT INTO account (id, account_type)
VALUES ('acc-003', 'GOLD');

INSERT INTO transaction (id, account_id, created_at, transaction_type, amount, reason)
VALUES ('txn-001', 'acc-001', now(), 'IN', 500.00, 'Dépôt initial');

INSERT INTO transaction (id, account_id, created_at, transaction_type, amount, reason)
VALUES ('txn-002', 'acc-001', now(), 'OUT', 120.50, 'Retrait guichet');

INSERT INTO transaction (id, account_id, created_at, transaction_type, amount, reason)
VALUES ('txn-003', 'acc-002', now(), 'IN', 1000.00, 'Virement reçu');