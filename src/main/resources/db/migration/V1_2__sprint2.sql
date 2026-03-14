ALTER TABLE portfolio_images
    ADD COLUMN image_url TEXT;
    ALTER COLUMN media_id DROP NOT NULL;