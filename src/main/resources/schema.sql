create table ref_item (
  id int auto_increment primary key,
  code varchar(10),
  name varchar(50)
);

create table ref_place (
  id int auto_increment primary key,
  code varchar(10),
  name varchar(50)
);

create table doc_move (
  id int auto_increment primary key,
  doc_date timestamp,
  doc_num varchar(10),
  ref_place_from_id int references ref_place (id),
  ref_place_to_id int references ref_place (id)
);

create table doc_move_tab (
  id int auto_increment primary key,
  doc_move_id int not null references doc_move (id),
  ref_item_id int not null references ref_item (id),
  quantity int
);

create view rep_item_balance_v as
with
WDetail as (
  select dm.ref_place_from_id as ref_place_id,
    dmt.ref_item_id,
    -quantity as quantity
  from doc_move dm
    join doc_move_tab dmt on dmt.doc_move_id = dm.id
  union all
  select dm.ref_place_to_id as ref_place_id,
    dmt.ref_item_id,
    quantity as quantity
  from doc_move dm
    join doc_move_tab dmt on dmt.doc_move_id = dm.id
),
WReport as (
select ref_place_id,
  ref_item_id,
  cast(sum(quantity) as int) quantity
from WDetail
where ref_place_id is not null
group by ref_place_id,
  ref_item_id
)
select rownum as id,
  ref_place_id,
  ref_item_id,
  quantity
from WReport;
