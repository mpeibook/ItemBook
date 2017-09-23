-- ref_item
insert into ref_item (code, name) values ('I1', 'Notebook');
insert into ref_item (code, name) values ('I2', 'PC');
insert into ref_item (code, name) values ('I3', 'Smartphone');

-- ref_place
insert into ref_place (code, name) values ('P1', 'Store');
insert into ref_place (code, name) values ('P2', 'Room');

-- doc_move
insert into doc_move (doc_date, doc_num, ref_place_from_id, ref_place_to_id)
values (date '2017-01-01', 'D1', null, (select id from ref_place where code = 'P1'));

insert into doc_move (doc_date, doc_num, ref_place_from_id, ref_place_to_id)
values (date '2017-01-02', 'D2', (select id from ref_place where code = 'P1'), (select id from ref_place where code = 'P2'));

insert into doc_move (doc_date, doc_num, ref_place_from_id, ref_place_to_id)
values (date '2017-01-15', 'D3', (select id from ref_place where code = 'P2'), null);

-- doc_move_tab
insert into doc_move_tab (doc_move_id, ref_item_id, quantity)
  values ((select id from doc_move where doc_num = 'D1'), (select id from ref_item where code = 'I1'), 3);

insert into doc_move_tab (doc_move_id, ref_item_id, quantity)
  values ((select id from doc_move where doc_num = 'D1'), (select id from ref_item where code = 'I2'), 1);

insert into doc_move_tab (doc_move_id, ref_item_id, quantity)
  values ((select id from doc_move where doc_num = 'D2'), (select id from ref_item where code = 'I1'), 2);

insert into doc_move_tab (doc_move_id, ref_item_id, quantity)
  values ((select id from doc_move where doc_num = 'D3'), (select id from ref_item where code = 'I1'), 1);
