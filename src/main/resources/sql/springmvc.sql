 create sequence tb_course_id_seq increment by 1 minvalue 1 no maxvalue start with 4;  

create sequence tb_items_id_seq increment by 1 minvalue 1 no maxvalue start with 4;  
create sequence tb_orderdetail_id_seq increment by 1 minvalue 1 no maxvalue start with 5;  
create sequence tb_orders_id_seq increment by 1 minvalue 1 no maxvalue start with 2;  
create sequence tb_student_id_seq increment by 1 minvalue 1 no maxvalue start with 3;    
create sequence tb_user_id_seq increment by 1 minvalue 1 no maxvalue start with 13;  

-- Table: public.tb_course

-- DROP TABLE public.tb_course;

CREATE TABLE public.tb_course
(
  id character varying(20) NOT NULL,
  cname character varying(20),
  CONSTRAINT tb_course_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tb_course
  OWNER TO postgres;
COMMENT ON TABLE public.tb_course
  IS '课程表';

-- Table: public.tb_items

-- DROP TABLE public.tb_items;

CREATE TABLE public.tb_items
(
  id character varying(11) NOT NULL,
  name character varying(32) NOT NULL,
  price double precision NOT NULL,
  detail text,
  pic character varying(64),
  createtime date,
  CONSTRAINT tb_items_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tb_items
  OWNER TO postgres;
COMMENT ON TABLE public.tb_items
  IS '商品';

-- Table: public.tb_orderdetail

-- DROP TABLE public.tb_orderdetail;

CREATE TABLE public.tb_orderdetail
(
  id character varying(10) NOT NULL,
  orders_id character varying(11) NOT NULL,
  items_id character varying(11) NOT NULL,
  items_num bigint,
  CONSTRAINT tb_orderdetail_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tb_orderdetail
  OWNER TO postgres;
COMMENT ON TABLE public.tb_orderdetail
  IS '订单明细表';


CREATE TABLE public.tb_orders
(
  user_id character varying(11) NOT NULL,
  id character varying(11) NOT NULL,
  order_number character varying(20) NOT NULL,
  createtime date,
  note character varying(100),
  CONSTRAINT tb_orders_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tb_orders
  OWNER TO postgres;
COMMENT ON TABLE public.tb_orders
  IS '订单表';

-- Table: public.tb_student

-- DROP TABLE public.tb_student;

CREATE TABLE public.tb_student
(
  id character varying(11) NOT NULL,
  name character varying(50) NOT NULL,
  CONSTRAINT tb_student_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tb_student
  OWNER TO postgres;
COMMENT ON TABLE public.tb_student
  IS '学生表';

-- Table: public.tb_student_course

-- DROP TABLE public.tb_student_course;

CREATE TABLE public.tb_student_course
(
  s_id character varying(11) NOT NULL,
  c_id character varying(11) NOT NULL,
  CONSTRAINT tb_student_course_pk PRIMARY KEY (s_id, c_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tb_student_course
  OWNER TO postgres;
COMMENT ON TABLE public.tb_student_course
  IS '学生课程表';

-- Table: public.tb_user

-- DROP TABLE public.tb_user;

CREATE TABLE public.tb_user
(
  id character varying(11) NOT NULL,
  user_name character varying(50) NOT NULL,
  age bigint NOT NULL,
  password character varying(50) NOT NULL,
  CONSTRAINT tb_user_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tb_user
  OWNER TO postgres;
COMMENT ON TABLE public.tb_user
  IS '用户表';