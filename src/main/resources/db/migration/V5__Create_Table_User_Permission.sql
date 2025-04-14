CREATE TABLE public.user_permission (
    id_user BIGINT NOT NULL,
    id_permission BIGINT NOT NULL,
    CONSTRAINT fk_permission FOREIGN KEY (id_permission) REFERENCES public.permission (id),
    CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES public.users (id)
);