create table ref_item (
  id int auto_increment not null primary key,
  code varchar(10) not null unique,
  name varchar(50)
);

create table ref_place (
  id int auto_increment not null primary key,
  code varchar(10) not null unique,
  name varchar(50)
);

create table doc_move (
  id int auto_increment not null primary key,
  doc_date timestamp not null,
  doc_year int as (extract(year from doc_date)),
  doc_num varchar(10) not null,
  ref_place_from_id int references ref_place (id),
  ref_place_to_id int references ref_place (id)
);

alter table doc_move add constraint doc_move_uk unique (doc_year, doc_num);

create table doc_move_tab (
  id int auto_increment not null primary key,
  doc_move_id int not null references doc_move (id),
  ref_item_id int not null references ref_item (id),
  quantity int not null check (quantity > 0)
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
