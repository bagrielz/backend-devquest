ALTER TABLE public.users
    ADD COLUMN activity_statistics_id BIGINT,
    ADD COLUMN user_profile_id BIGINT;

ALTER TABLE public.users
    ADD CONSTRAINT ukm6rqhr1nq3b6j8aglc4tlhaj6 UNIQUE (user_profile_id),
    ADD CONSTRAINT uknkutixea86a5lui4nreunnd51 UNIQUE (activity_statistics_id),
    ADD CONSTRAINT fkgvxffhxghiogrd4g2sqompabh FOREIGN KEY (user_profile_id)
        REFERENCES public.user_profile (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    ADD CONSTRAINT fkj5hp2r8h27wi658dr2h53f82j FOREIGN KEY (activity_statistics_id)
        REFERENCES public.activity_statistics (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;
