CREATE SEQUENCE public.hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 4
  CACHE 1;
ALTER TABLE public.hibernate_sequence
  OWNER TO postgres;

CREATE TABLE public.diaria
(
  id bigint NOT NULL,
  periodo date,
  hospede_id bigint,
  contabilizado boolean,
  CONSTRAINT contabilizacao_diaria_pk PRIMARY KEY (id)
)

CREATE TABLE public.checkin
(
  id bigint NOT NULL,
  data_entrada timestamp without time zone,
  data_saida timestamp without time zone,
  adicional_veiculo boolean DEFAULT false,
  hospede_id bigint,
  total double precision DEFAULT 0,
  CONSTRAINT checkin_pkey PRIMARY KEY (id),
  CONSTRAINT hospede_fk FOREIGN KEY (hospede_id)
      REFERENCES public.hospede (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)

CREATE TABLE public.hospede
(
  id bigint NOT NULL,
  nome character varying,
  documento character varying,
  telefone character varying,
  valor_gasto double precision DEFAULT 0.0,
  CONSTRAINT hospede_pkey PRIMARY KEY (id),
  CONSTRAINT documento_unico UNIQUE (documento)
)