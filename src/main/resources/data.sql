INSERT IGNORE role VALUES (1, 'Admin'), (2, 'User');
INSERT IGNORE operation_type VALUES (1, 'Transfer'), (2, 'Income'), (3, 'Expense');
INSERT IGNORE currency VALUES (1, 'USD'), (2, 'UAH');
INSERT IGNORE group_of_categories VALUES (1, 'Food&Drinks'), (2, 'Shopping'), (3, 'Income');
INSERT IGNORE category VALUES (1, 'Groceries', 1), (2, 'Clothes&Shoes', 2), (3, 'Salary', 3), (4, 'Gifts', 3), (5, 'Caffe and bars', 1);
INSERT IGNORE user VALUES (1, 'Alex', 'Smith', 2), (2, 'John', 'Allen', 2), (3, 'Felix', 'Miller', 2);
INSERT IGNORE account (id, balance, number, currency_id, user_id)
VALUES (1, 2000, '5457 2121 0323 1111', 1, 1),
       (2, 1500, '5457 2121 0323 2222', 2, 1),
       (3, 1000, '5457 2121 0323 3333', 1, 2),
       (4, 1800, '5457 2121 0323 4444', 2, 2),
       (5, 2500, '5457 2121 0323 5555', 1, 3),
       (6, 2750, '5457 2121 0323 6666', 2, 3);
INSERT IGNORE operation (id, date, description, price, account_id, category_id, operation_type_id, transfer_to , total_for_transfer)
VALUES (1, '2021-07-12 12:00:00', 'Salary', 1000, 1, 3, 2, NULL, NULL),
       (2, '2021-08-07 12:09:33', 'Dinner in the restaurant', 300, 1, 1, 3, NULL, NULL),
       (3, '2021-06-04 11:04:33', 'Transfer to my account', NULL, 1, NULL, 1, 2, 1000),

       (4, '2021-06-12 15:04:14', 'Income from deposit', 1000, 2, 1, 3, NULL, NULL),
       (5, '2021-05-25 12:04:23', 'Bought new sneakers', 800, 2, 2, 3, NULL, NULL),
       (6, '2021-03-07 17:04:33', 'Transfer to my account', NULL, 2, NULL, 1, 1, 500),

       (7, '2021-08-06 12:34:39', 'My salary', 1500, 3, 3, 2, NULL, NULL),
       (8, '2021-08-03 16:04:31', 'Groceries for 3 days', 500, 3, 1, 3, NULL, NULL),
       (9, '2021-08-01 17:08:22', 'Transfer to my account', NULL, 3, NULL, 1, 4, 1000),

       (10, '2021-08-01 16:34:02', 'Gift from bro', 800, 4, 4, 2, NULL, NULL),
       (11, '2021-05-12 13:12:34', 'New T-Shirt', 300, 4, 2, 3, NULL, NULL),
       (12, '2021-08-02 14:56:21', 'Transfer to my account', NULL, 4, NULL, 1, 3, 750),

       (13, '2021-03-07 09:12:54', 'Month salary', 1800, 5, 3, 2, NULL, NULL),
       (14, '2021-08-01 10:32:22', 'Morning coffee', 200, 5, 5, 3, NULL, NULL),
       (15, '2021-08-02 02:54:23', 'Transfer to my account', NULL, 5, NULL, 1, 6, 1000),

       (16, '2021-08-03 11:11:12', 'Dividends', 2000, 6, 3, 2, NULL, NULL),
       (17, '2021-08-04 15:05:55', 'Friday bar', 800, 6, 5, 3, NULL, NULL),
       (18, '2021-07-29 17:21:32', 'Transfer to my account', NULL, 6, NULL, 1, 5, 800);