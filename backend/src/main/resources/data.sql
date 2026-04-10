-- ============================================================
--  SEED DATA - App de Zapatillas / Tienda Deportiva
--  Generado para desarrollo y pruebas
-- ============================================================

-- ============================================================
-- USUARIOS (18 usuarios: 1 admin + 17 usuarios normales)
-- ============================================================
INSERT INTO `user` (`name`, `surname`, `user_name`, `email`, `profile_img_url`, `date`, `password`, `role`) VALUES
  ('Admin',    'Test',       'admin',    'admin@example.com',    'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=100', '1975-03-15', '$2a$10$lKS4sIwdmasRHYBe30LyvO.8nugzhXP8quK1vOeaGCHFuODYLf1hq', 'ADMIN'),
  ('Hugo',     'Piramide',   'hugo',     'hugo@example.com',     'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=100', '1998-07-22', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER'),
  ('María',    'García',     'mgarcia',  'maria@example.com',    'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100', '1996-11-08', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER'),
  ('Carlos',   'Martínez',   'carlosmtz','carlos@example.com',   'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=100', '1993-05-14', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER'),
  ('Laura',    'López',      'laural',   'laura@example.com',    'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=100', '1999-02-28', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER'),
  ('Andrés',   'Sánchez',    'andres',   'andres@example.com',   'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=100', '1994-09-03', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER'),
  ('Sofía',    'Fernández',  'sofiaf',   'sofia@example.com',    'https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=100', '1997-12-19', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER'),
  ('Pablo',    'Ruiz',       'pabloruiz','pablo@example.com',    'https://images.unsplash.com/photo-1552058544-f2b08422138a?w=100', '1992-06-10', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER'),
  ('Elena',    'Torres',     'elenat',   'elena@example.com',    'https://images.unsplash.com/photo-1580489944761-15a19d654956?w=100', '2000-01-25', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER'),
  ('Diego',    'Ramírez',    'diegor',   'diego@example.com',    'https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=100', '1995-04-17', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER'),
  ('Valentina','Moreno',     'vale',     'valentina@example.com','https://images.unsplash.com/photo-1508214751196-bcfd4ca60f91?w=100', '1998-10-05', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER'),
  ('Javier',   'Jiménez',    'javierjm', 'javier@example.com',   'https://images.unsplash.com/photo-1534030347209-467a5b0ad3e6?w=100', '1991-08-12', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER'),
  ('Camila',   'Álvarez',    'camilaa',  'camila@example.com',   'https://images.unsplash.com/photo-1489424731084-a5d8b219a5bb?w=100', '1999-03-30', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER'),
  ('Sergio',   'Romero',     'sergioro', 'sergio@example.com',   'https://images.unsplash.com/photo-1545996124-0501ebae84d0?w=100', '1997-07-21', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER'),
  ('Natalia',  'Díaz',       'nataliad', 'natalia@example.com',  'https://images.unsplash.com/photo-1531746020798-e6953c6e8e04?w=100', '1994-12-07', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER'),
  ('Miguel',   'Herrera',    'miguelh',  'miguel@example.com',   'https://images.unsplash.com/photo-1463453091185-61582044d556?w=100', '1996-09-18', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER'),
  ('Isabella', 'Muñoz',      'isabellam','isabella@example.com', 'https://images.unsplash.com/photo-1548142813-c348350df52b?w=100', '2001-05-11', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER'),
  ('Rodrigo',  'Castro',     'rodrigoc', 'rodrigo@example.com',  'https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?w=100', '1993-11-26', '$2a$10$7lxYwKA.GSsuVYEFP2uznOJwqkaNB.NuWXpyvaunQHm5czT4gh3wm', 'USER');

-- ============================================================
-- CATEGORÍAS (6 categorías)
-- ============================================================
INSERT INTO `category` (`name`, `description`) VALUES
('Running',    'Equipamiento de alto rendimiento para corredores de todos los niveles.'),
('Training',   'Calzado y ropa diseñada para entrenamientos intensos en el gimnasio.'),
('Lifestyle',  'Moda deportiva para el día a día con el máximo confort.'),
('Basketball', 'Zapatillas y accesorios para jugadores de baloncesto.'),
('Hiking',     'Calzado resistente para rutas de montaña y trail.'),
('Football',   'Botas y equipación para fútbol indoor y césped natural.');

-- ============================================================
-- PRODUCTOS (20 productos)
-- ============================================================
INSERT INTO `product` (`name`, `description`, `base_price`, `image_url`, `active`, `category_id`) VALUES
-- Running (cat 1)
('Swift Runner Pro',       'Zapatilla ligera con amortiguación premium para maratones.',                     129.99, 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400', 1, 1),
('AeroStep 360',           'Tecnología de retorno de energía para runners de larga distancia.',             119.00, 'https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=400', 1, 1),
('SpeedTrack Elite',       'Plantilla ergonómica y suela de carbono para tiempos récord.',                  145.00, 'https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=400', 1, 1),
('CloudRun Flex',          'Diseño ultraligero con malla transpirable ideal para calor.',                    99.99, 'https://images.unsplash.com/photo-1600185365926-3a2ce3cdb9eb?w=400', 1, 1),

-- Training (cat 2)
('Grit Master X',          'Construidas para resistir los entrenamientos de crossfit más duros.',           110.00, 'https://images.unsplash.com/photo-1514989940723-e8e51635b782?w=400', 1, 2),
('IronGrip Pro',           'Suela multidireccional para levantamiento de pesas y funcional.',               105.50, 'https://images.unsplash.com/photo-1579338559194-a162d19bf842?w=400', 1, 2),
('FlexCore V2',            'Máxima estabilidad lateral para HIIT y entrenamientos en circuito.',             95.00, 'https://images.unsplash.com/photo-1551107696-a4b0c5a0d9a2?w=400', 1, 2),

-- Lifestyle (cat 3)
('Urban Glide',            'Estilo minimalista perfecto para la ciudad y caminatas largas.',                 85.50, 'https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?w=400', 1, 3),
('Neo Classic',            'Clásico reinventado: cuero vegano y suela vulcanizada moderna.',                 79.99, 'https://images.unsplash.com/photo-1460353581641-37baddab0fa2?w=400', 1, 3),
('Drift Low',              'Silueta baja con colorways exclusivos para destacar en la calle.',               89.00, 'https://images.unsplash.com/photo-1491553895911-0055eca6402d?w=400', 1, 3),
('Retro Wave',             'Inspirada en los 90s con tecnología de amortiguación actual.',                   92.00, 'https://images.unsplash.com/photo-1584735175315-9d5df23be7be?w=400', 1, 3),

-- Basketball (cat 4)
('Elite Basketball',       'Máxima tracción y soporte para jugadores explosivos.',                          150.00, 'https://images.unsplash.com/photo-1543508282-6319a3e2621f?w=400', 1, 4),
('Court King Mid',         'Tobillera alta reforzada y amortiguación reactiva para el pivote.',             135.00, 'https://images.unsplash.com/photo-1575537302964-96cd47c06b1b?w=400', 1, 4),
('Slam Dunk Low',          'Perfil bajo para jugadores de perímetro que priorizan velocidad.',              125.00, 'https://images.unsplash.com/photo-1556906781-9a412961a28c?w=400', 1, 4),

-- Hiking (cat 5)
('TrailBlazer GTX',        'Membrana impermeable Gore-Tex y puntera reforzada para trail duro.',            159.99, 'https://images.unsplash.com/photo-1520639888713-7851133b1ed0?w=400', 1, 5),
('Summit X Low',           'Ligera y con suela Vibram para senderos mixtos y roca.',                       139.00, 'https://images.unsplash.com/photo-1553361371-9b22f78e8b1d?w=400', 1, 5),
('Mudrunner 2.0',          'Agarre extremo en barro con sistema de drenaje rápido.',                       115.00, 'https://images.unsplash.com/photo-1551698618-1dfe5d97d256?w=400', 1, 5),

-- Football (cat 6)
('Strike Force FG',        'Bota de tacos para césped natural con zona de golpeo reforzada.',              120.00, 'https://images.unsplash.com/photo-1511886929837-354d827aae26?w=400', 1, 6),
('Futsal Flash IN',        'Suela lisa de goma de alta adherencia para fútbol sala.',                       89.99, 'https://images.unsplash.com/photo-1574629810360-7efbbe195018?w=400', 1, 6),
('Turf Rocket TF',         'Suela multitaco para superficies sintéticas y tierra.',                         98.00, 'https://images.unsplash.com/photo-1560272564-c83b66b1ad12?w=400', 0, 6); -- producto inactivo

-- ============================================================
-- VARIANTES DE PRODUCTO (tallas / stock / modificador precio)
-- ============================================================
INSERT INTO `product_variant` (`product_id`, `size`, `stock`, `price_modifier`) VALUES
-- Swift Runner Pro (id 1)
(1, '38', 5, 0.0), (1, '39', 8, 0.0), (1, '40', 10, 0.0),
(1, '41', 12, 0.0), (1, '42', 15, 0.0), (1, '43', 7, 0.0), (1, '44', 5, 5.0),

-- AeroStep 360 (id 2)
(2, '39', 6, 0.0), (2, '40', 9, 0.0), (2, '41', 11, 0.0),
(2, '42', 10, 0.0), (2, '43', 4, 0.0), (2, '44', 3, 5.0),

-- SpeedTrack Elite (id 3)
(3, '40', 4, 0.0), (3, '41', 6, 0.0), (3, '42', 8, 0.0),
(3, '43', 5, 0.0), (3, '44', 2, 5.0), (3, '45', 1, 5.0),

-- CloudRun Flex (id 4)
(4, '37', 7, 0.0), (4, '38', 9, 0.0), (4, '39', 12, 0.0),
(4, '40', 15, 0.0), (4, '41', 10, 0.0), (4, '42', 8, 0.0),

-- Grit Master X (id 5)
(5, 'S', 10, -5.0), (5, 'M', 20, 0.0), (5, 'L', 12, 0.0), (5, 'XL', 6, 0.0),

-- IronGrip Pro (id 6)
(6, 'S', 8, -5.0), (6, 'M', 14, 0.0), (6, 'L', 10, 0.0), (6, 'XL', 5, 0.0), (6, 'XXL', 2, 5.0),

-- FlexCore V2 (id 6)
(7, 'S', 6, 0.0), (7, 'M', 18, 0.0), (7, 'L', 9, 0.0), (7, 'XL', 4, 0.0),

-- Urban Glide (id 8)
(8, '38', 8, 0.0), (8, '39', 12, 0.0), (8, '40', 25, 0.0),
(8, '41', 20, 0.0), (8, '42', 10, 0.0), (8, '43', 5, 0.0),

-- Neo Classic (id 9)
(9, '37', 10, 0.0), (9, '38', 15, 0.0), (9, '39', 18, 0.0),
(9, '40', 20, 0.0), (9, '41', 12, 0.0), (9, '42', 8, 0.0),

-- Drift Low (id 10)
(10, '38', 9, 0.0), (10, '39', 11, 0.0), (10, '40', 14, 0.0),
(10, '41', 10, 0.0), (10, '42', 7, 0.0), (10, '43', 3, 0.0),

-- Retro Wave (id 11)
(11, '38', 6, 0.0), (11, '39', 8, 0.0), (11, '40', 10, 0.0),
(11, '41', 9, 0.0), (11, '42', 5, 0.0),

-- Elite Basketball (id 12)
(12, '40', 5, 0.0), (12, '41', 7, 0.0), (12, '42', 9, 0.0),
(12, '43', 4, 0.0), (12, '44', 3, 0.0), (12, '45', 2, 10.0),

-- Court King Mid (id 13)
(13, '40', 4, 0.0), (13, '41', 6, 0.0), (13, '42', 8, 0.0),
(13, '43', 5, 0.0), (13, '44', 2, 0.0),

-- Slam Dunk Low (id 14)
(14, '39', 5, 0.0), (14, '40', 7, 0.0), (14, '41', 9, 0.0),
(14, '42', 6, 0.0), (14, '43', 3, 0.0),

-- TrailBlazer GTX (id 15)
(15, '39', 4, 0.0), (15, '40', 6, 0.0), (15, '41', 8, 0.0),
(15, '42', 5, 0.0), (15, '43', 3, 0.0), (15, '44', 2, 10.0),

-- Summit X Low (id 16)
(16, '38', 5, 0.0), (16, '39', 7, 0.0), (16, '40', 9, 0.0),
(16, '41', 7, 0.0), (16, '42', 4, 0.0), (16, '43', 2, 0.0),

-- Mudrunner 2.0 (id 17)
(17, '39', 6, 0.0), (17, '40', 8, 0.0), (17, '41', 10, 0.0),
(17, '42', 7, 0.0), (17, '43', 3, 0.0),

-- Strike Force FG (id 18)
(18, '38', 7, 0.0), (18, '39', 9, 0.0), (18, '40', 11, 0.0),
(18, '41', 8, 0.0), (18, '42', 5, 0.0), (18, '43', 2, 0.0),

-- Futsal Flash IN (id 19)
(19, '38', 8, 0.0), (19, '39', 10, 0.0), (19, '40', 12, 0.0),
(19, '41', 9, 0.0), (19, '42', 6, 0.0),

-- Turf Rocket TF (id 20) - producto inactivo, poco stock
(20, '40', 2, 0.0), (20, '41', 1, 0.0), (20, '42', 1, 0.0);

-- ============================================================
-- PEDIDOS (25 pedidos con distintos estados y usuarios)
-- ============================================================
INSERT INTO `orders` (`user_id`, `order_date`, `status`, `total_price`, `shipping_addres`) VALUES
-- hugo (user 2)
(2,  '2024-01-15 10:30:00', 'ENTREGADO',  129.99, 'Calle Falsa 123, Madrid'),
(2,  '2024-03-20 14:15:00', 'PROCESANDO', 195.50, 'Avenida Siempre Viva 742, Barcelona'),
(2,  '2024-02-28 09:45:00', 'ENVIADO',    150.00, 'Calle Gran Vía 10, Madrid'),
-- mgarcia (user 3)
(3,  '2024-01-08 11:20:00', 'ENTREGADO',  264.99, 'Paseo de la Castellana 88, Madrid'),
(3,  '2024-01-22 15:50:00', 'CANCELADO',   85.50, 'Calle Serrano 45, Madrid'),
-- carlosmtz (user 4)
(4,  '2024-02-05 13:30:00', 'ENTREGADO',  220.00, 'Avenida Diagonal 350, Barcelona'),
(4,  '2024-03-18 10:00:00', 'PROCESANDO', 145.00, 'Carrer de Balmes 78, Barcelona'),
-- laural (user 5)
(5,  '2024-02-14 16:45:00', 'ENVIADO',    179.98, 'Plaza Mayor 1, Salamanca'),
(5,  '2024-01-30 12:15:00', 'ENTREGADO',   99.99, 'Calle Rúa 5, Santiago de Compostela'),
-- andres (user 6)
(6,  '2024-03-22 09:30:00', 'PROCESANDO', 310.00, 'Gran Vía 1, Bilbao'),
(6,  '2024-02-10 14:20:00', 'ENTREGADO',  159.99, 'Calle Ercilla 14, Bilbao'),
-- sofiaf (user 7)
(7,  '2024-02-25 11:10:00', 'ENVIADO',    169.99, 'Calle Colón 20, Valencia'),
(7,  '2024-01-20 15:40:00', 'ENTREGADO',  105.50, 'Avenida del Puerto 33, Valencia'),
-- pabloruiz (user 8)
(8,  '2024-01-12 10:05:00', 'CANCELADO',  139.00, 'Calle Larios 7, Málaga'),
(8,  '2024-02-20 13:25:00', 'ENTREGADO',  240.00, 'Paseo del Parque 2, Málaga'),
-- elenat (user 9)
(9,  '2024-03-19 08:50:00', 'PROCESANDO', 89.00,  'Calle Real 11, Valladolid'),
(9,  '2024-02-03 12:35:00', 'ENTREGADO',  215.50, 'Paseo de Zorrilla 90, Valladolid'),
-- diegor (user 10)
(10, '2024-03-05 14:15:00', 'ENVIADO',    274.99, 'Calle Alfonso I 20, Zaragoza'),
(10, '2024-01-25 10:40:00', 'ENTREGADO',  120.00, 'Paseo de la Independencia 5, Zaragoza'),
-- vale (user 11)
(11, '2024-02-12 16:20:00', 'ENTREGADO',  184.99, 'Calle Tetuán 8, Sevilla'),
(11, '2024-03-17 11:55:00', 'PROCESANDO', 145.00, 'Avenida de la Constitución 1, Sevilla'),
-- javierjm (user 12)
(12, '2024-01-18 09:30:00', 'ENTREGADO',  299.99, 'Passeig de Gràcia 100, Barcelona'),
-- camilaa (user 13)
(13, '2024-03-10 13:45:00', 'ENVIADO',    110.00, 'Calle Preciados 3, Madrid'),
-- sergioro (user 14)
(14, '2024-02-07 15:10:00', 'ENTREGADO',  175.50, 'Gran Vía de Colón 10, Granada'),
-- miguelh (user 16)
(16, '2024-03-21 10:25:00', 'PROCESANDO', 259.99, 'Calle Marqués de Larios 1, Málaga');

-- ============================================================
-- ORDER ITEMS
-- Referencia de variantes más usadas (IDs aproximados según orden de inserción):
--  Swift Runner 40=1, 42=3  | AeroStep 39=8 | SpeedTrack 42=15
--  CloudRun 40=22 | GritMaster M=29 | IronGrip M=33 | FlexCore M=37
--  UrbanGlide 40=42 | NeoCla 40=48 | DriftLow 40=53 | RetroWave 40=57
--  EliteBasket 42=62 | CourtKing 42=67 | SlamDunk 41=72 | TrailBlazer 41=77
--  SummitX 40=83 | Mudrunner 41=89 | StrikeForce 40=94 | Futsal 40=99
-- ============================================================
INSERT INTO `order_items` (`order_id`, `product_variant_id`, `quantity`, `price_at_purchase`, `product_name`, `product_size`, `product_image_url`, `base_price`, `price_modifier`) VALUES
-- Pedido 1: hugo - ENTREGADO
(1, 1,  1, 129.99, 'Swift Runner Pro', '40', 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400', 129.99, 0.0),
-- Pedido 2: hugo - PROCESANDO
(2, 29, 1, 110.00, 'Grit Master X', 'M', 'https://images.unsplash.com/photo-1514989940723-e8e51635b782?w=400', 115.00, -5.0),
(2, 42, 1,  85.50, 'Urban Glide', '40', 'https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?w=400', 85.50, 0.0),
-- Pedido 3: hugo - ENVIADO
(3, 62, 1, 150.00, 'Elite Basketball', '42', 'https://images.unsplash.com/photo-1543508282-6319a3e2621f?w=400', 150.00, 0.0),
-- Pedido 4: mgarcia - ENTREGADO
(4, 1,  1, 129.99, 'Swift Runner Pro', '40', 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400', 129.99, 0.0),
(4, 57, 1,  92.00, 'Retro Wave', '40', 'https://images.unsplash.com/photo-1584735175315-9d5df23be7be?w=400', 92.00, 0.0),
(4, 53, 1,  89.00, 'Drift Low', '40', 'https://images.unsplash.com/photo-1491553895911-0055eca6402d?w=400', 89.00, 0.0),  -- 264.99 aprox (con descuentos hipotéticos)
-- Pedido 5: mgarcia - CANCELADO
(5, 42, 1,  85.50, 'Urban Glide', '40', 'https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?w=400', 85.50, 0.0),
-- Pedido 6: carlosmtz - ENTREGADO
(6, 29, 1, 110.00, 'Grit Master X', 'M', 'https://images.unsplash.com/photo-1514989940723-e8e51635b782?w=400', 115.00, -5.0),
(6, 33, 1, 105.50, 'IronGrip Pro', 'M', 'https://images.unsplash.com/photo-1579338559194-a162d19bf842?w=400', 110.50, -5.0),
-- Pedido 7: carlosmtz - PROCESANDO
(7, 15, 1, 145.00, 'SpeedTrack Elite', '42', 'https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=400', 145.00, 0.0),
-- Pedido 8: laural - ENVIADO
(8, 48, 1,  79.99, 'Neo Classic', '40', 'https://images.unsplash.com/photo-1460353581641-37baddab0fa2?w=400', 79.99, 0.0),
(8, 22, 1,  99.99, 'CloudRun Flex', '40', 'https://images.unsplash.com/photo-1600185365926-3a2ce3cdb9eb?w=400', 99.99, 0.0),
-- Pedido 9: laural - ENTREGADO
(9, 22, 1,  99.99, 'CloudRun Flex', '40', 'https://images.unsplash.com/photo-1600185365926-3a2ce3cdb9eb?w=400', 99.99, 0.0),
-- Pedido 10: andres - PROCESANDO
(10, 62, 1, 150.00, 'Elite Basketball', '42', 'https://images.unsplash.com/photo-1543508282-6319a3e2621f?w=400', 150.00, 0.0),
(10, 67, 1, 135.00, 'Court King Mid', '42', 'https://images.unsplash.com/photo-1575537302964-96cd47c06b1b?w=400', 135.00, 0.0),
(10, 83, 1,  25.00, 'Summit X Low', '40', 'https://images.unsplash.com/photo-1553361371-9b22f78e8b1d?w=400', 139.00, 0.0),  -- accesorio add-on
-- Pedido 11: andres - ENTREGADO
(11, 77, 1, 159.99, 'TrailBlazer GTX', '41', 'https://images.unsplash.com/photo-1520639888713-7851133b1ed0?w=400', 159.99, 0.0),
-- Pedido 12: sofiaf - ENVIADO
(12, 53, 1,  89.00, 'Drift Low', '40', 'https://images.unsplash.com/photo-1491553895911-0055eca6402d?w=400', 89.00, 0.0),
(12, 48, 1,  79.99, 'Neo Classic', '40', 'https://images.unsplash.com/photo-1460353581641-37baddab0fa2?w=400', 79.99, 0.0),
-- Pedido 13: sofiaf - ENTREGADO
(13, 33, 1, 105.50, 'IronGrip Pro', 'M', 'https://images.unsplash.com/photo-1579338559194-a162d19bf842?w=400', 110.50, -5.0),
-- Pedido 14: pabloruiz - CANCELADO
(14, 83, 1, 139.00, 'Summit X Low', '40', 'https://images.unsplash.com/photo-1553361371-9b22f78e8b1d?w=400', 139.00, 0.0),
-- Pedido 15: pabloruiz - ENTREGADO
(15, 62, 1, 150.00, 'Elite Basketball', '42', 'https://images.unsplash.com/photo-1543508282-6319a3e2621f?w=400', 150.00, 0.0),
(15, 15, 1,  90.00, 'SpeedTrack Elite', '42', 'https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=400', 145.00, 0.0),
-- Pedido 16: elenat - PROCESANDO
(16, 53, 1,  89.00, 'Drift Low', '40', 'https://images.unsplash.com/photo-1491553895911-0055eca6402d?w=400', 89.00, 0.0),
-- Pedido 17: elenat - ENTREGADO
(17, 29, 2, 110.00, 'Grit Master X', 'M', 'https://images.unsplash.com/photo-1514989940723-e8e51635b782?w=400', 115.00, -5.0),
(17, 42, 1,  85.50, 'Urban Glide', '40', 'https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?w=400', 85.50, 0.0),  -- 2x110 + 85.50 ≈ 305.50
-- Pedido 18: diegor - ENVIADO
(18, 1,  1, 129.99, 'Swift Runner Pro', '40', 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400', 129.99, 0.0),
(18, 57, 1,  92.00, 'Retro Wave', '40', 'https://images.unsplash.com/photo-1584735175315-9d5df23be7be?w=400', 92.00, 0.0),
(18, 48, 1,  79.99, 'Neo Classic', '40', 'https://images.unsplash.com/photo-1460353581641-37baddab0fa2?w=400', 79.99, 0.0),  -- 301.98 aprox
-- Pedido 19: diegor - ENTREGADO
(19, 94, 1, 120.00, 'Strike Force FG', '40', 'https://images.unsplash.com/photo-1511886929837-354d827aae26?w=400', 120.00, 0.0),
-- Pedido 20: vale - ENTREGADO
(20, 53, 1,  89.00, 'Drift Low', '40', 'https://images.unsplash.com/photo-1491553895911-0055eca6402d?w=400', 89.00, 0.0),
(20, 48, 1,  79.99, 'Neo Classic', '40', 'https://images.unsplash.com/photo-1460353581641-37baddab0fa2?w=400', 79.99, 0.0),
-- Pedido 21: vale - PROCESANDO
(21, 15, 1, 145.00, 'SpeedTrack Elite', '42', 'https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=400', 145.00, 0.0),
-- Pedido 22: javierjm - ENTREGADO
(22, 77, 1, 159.99, 'TrailBlazer GTX', '41', 'https://images.unsplash.com/photo-1520639888713-7851133b1ed0?w=400', 159.99, 0.0),
(22, 83, 1, 139.00, 'Summit X Low', '40', 'https://images.unsplash.com/photo-1553361371-9b22f78e8b1d?w=400', 139.00, 0.0),
-- Pedido 23: camilaa - ENVIADO
(23, 29, 1, 110.00, 'Grit Master X', 'M', 'https://images.unsplash.com/photo-1514989940723-e8e51635b782?w=400', 115.00, -5.0),
-- Pedido 24: sergioro - ENTREGADO
(24, 42, 1,  85.50, 'Urban Glide', '40', 'https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?w=400', 85.50, 0.0),
(24, 57, 1,  92.00, 'Retro Wave', '40', 'https://images.unsplash.com/photo-1584735175315-9d5df23be7be?w=400', 92.00, 0.0),
-- Pedido 25: miguelh - PROCESANDO
(25, 1,  1, 129.99, 'Swift Runner Pro', '40', 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400', 129.99, 0.0),
(25, 67, 1, 135.00, 'Court King Mid', '42', 'https://images.unsplash.com/photo-1575537302964-96cd47c06b1b?w=400', 135.00, 0.0);

-- ============================================================
-- FAVORITOS (35 registros en distintas combinaciones)
-- ============================================================
INSERT INTO `user_favorites` (`user_id`, `product_id`, `notify_when_in_stock`) VALUES
-- hugo (2)
(2,  4,  1),
(2,  1,  0),
(2,  12, 1),
-- mgarcia (3)
(3,  8,  0),
(3,  9,  0),
(3,  11, 1),
(3,  3,  1),
-- carlosmtz (4)
(4,  5,  0),
(4,  6,  1),
(4,  12, 0),
(4,  15, 1),
-- laural (5)
(5,  9,  0),
(5,  10, 1),
(5,  8,  0),
-- andres (6)
(6,  12, 1),
(6,  13, 1),
(6,  15, 0),
(6,  17, 1),
-- sofiaf (7)
(7,  10, 0),
(7,  11, 1),
(7,  9,  0),
-- pabloruiz (8)
(8,  15, 1),
(8,  16, 1),
(8,  1,  0),
-- elenat (9)
(9,  8,  0),
(9,  10, 1),
-- diegor (10)
(10, 18, 1),
(10, 19, 0),
(10, 1,  1),
-- vale (11)
(11, 9,  0),
(11, 11, 1),
-- javierjm (12)
(12, 12, 1),
(12, 13, 0),
-- sergioro (14)
(14, 8,  0),
(14, 3,  1);
