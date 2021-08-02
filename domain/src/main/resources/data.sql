INSERT IGNORE role VALUES (1, 'Admin'), (2, 'User');
INSERT IGNORE operation_type VALUES (1, 'Transfer'), (2, 'Income'), (3, 'Expense');
INSERT IGNORE currency VALUES (1, 'USD'), (2, 'UAH');
INSERT IGNORE group_of_categories VALUES (1, 'Food&Drinks'), (2, 'Shopping');
INSERT IGNORE category VALUES (1, 'Groceries', 1), (2, 'Clothes&Shoes', 2);
INSERT IGNORE user VALUES (1, 'Alex', 'Smith', 2), (2, 'John', 'Allen', 1);
INSERT IGNORE account (balance, number, currency_id, user_id) VALUES (200.25, 24325434, 1, 1), (310, 34541212, 2, 1), (55.50, 23124545, 2, 2);
INSERT IGNORE operation (date, description, price, account_id, category_id, operation_type_id) VALUES (NOW(), 'Bought a new shoes.', 215, 2, 2, 3), (NOW(), 'Dinner in the restaurant', 20, 3, 1, 3);