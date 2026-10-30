-- =============================================================================
-- DESO 2026 - Trabajo Práctico: Gestión de Capital Humano
-- FASE 2.2: Script de Datos de Prueba (seed.sql) para PostgreSQL / Supabase
-- =============================================================================

-- 0. CONFIGURACIÓN BASE DEL CUESTIONARIO
INSERT INTO configuracion_cuestionario (preguntas_por_bloque, tiempo_maximo_minutos, maximo_accesos, preguntas_por_factor)
VALUES (4, 60, 3, 2);

-- =============================================================================
-- 1. CONSULTORES DE PRUEBA (CU 01)
-- =============================================================================
INSERT INTO usuario_consultor (nombre_usuario, password, nombre, apellido, email) VALUES
('admin', 'admin123', 'Carlos', 'Gómez', 'cgomez@capitalhumano.com'),
('consultor1', 'cons1234', 'Laura', 'Martínez', 'lmartinez@capitalhumano.com');

-- =============================================================================
-- 2. CANDIDATOS DE PRUEBA (CU 01, CU 06, CU 25)
-- =============================================================================
INSERT INTO candidato (nro_candidato, apellido, nombre, tipo_doc, nro_doc, fecha_nac, genero, nacionalidad, email, escolaridad, eliminado) VALUES
(1001, 'Gervasio', 'Lucas', 'DNI', '44761951', '2003-03-01', 'H', 'Argentina', 'lgervasio@mail.com', 'Universitario en curso', FALSE),
(1002, 'Beltrán', 'Bruno', 'DNI', '45487341', '2004-04-16', 'H', 'Argentina', 'bbeltran@mail.com', 'Universitario en curso', FALSE),
(1003, 'Blanco', 'Facundo', 'DNI', '44731588', '2003-03-21', 'H', 'Argentina', 'fblanco@mail.com', 'Universitario en curso', FALSE),
(1004, 'Giupponi', 'Tomás', 'DNI', '46081756', '2004-09-08', 'H', 'Argentina', 'tgiupponi@mail.com', 'Universitario en curso', FALSE),
(1005, 'González', 'María Sol', 'DNI', '43222333', '2001-08-22', 'M', 'Argentina', 'mgonzalez@mail.com', 'Universitario completo', FALSE),
(1006, 'Fernández', 'Sofía', 'DNI', '44555666', '2002-02-19', 'M', 'Argentina', 'sfernandez@mail.com', 'Universitario en curso', FALSE),
(1007, 'Pérez', 'Juan Ignacio', 'DNI', '42111222', '2000-04-15', 'H', 'Argentina', 'jperez@mail.com', 'Terciario completo', FALSE);

-- =============================================================================
-- 3. COMPETENCIAS Y FACTORES (CU 13, CU 17)
-- =============================================================================
INSERT INTO competencia (codigo, nombre, descripcion, tipo, activa) VALUES
('COMP-01', 'Trabajo en Equipo', 'Capacidad para colaborar eficazmente con otros hacia objetivos comunes.', 'ACTITUDINAL', TRUE),
('COMP-02', 'Resolución de Problemas', 'Habilidad para analizar situaciones complejas y proponer soluciones técnicas.', 'TECNICA', TRUE),
('COMP-03', 'Liderazgo', 'Capacidad para guiar, motivar y coordinar equipos de trabajo.', 'ACTITUDINAL', TRUE);

-- Factores ordenados dentro de cada competencia
INSERT INTO factor (codigo, nombre, descripcion, nro_orden, codigo_competencia, activo) VALUES
('FAC-01', 'Comunicación Interpersonal', 'Claridad y escucha activa con pares.', 1, 'COMP-01', TRUE),
('FAC-02', 'Cooperación', 'Disposición a ayudar y compartir conocimientos.', 2, 'COMP-01', TRUE),
('FAC-03', 'Pensamiento Analítico', 'Desglose lógico de problemas técnicos.', 1, 'COMP-02', TRUE),
('FAC-04', 'Toma de Decisiones', 'Elección fundamentada entre alternativas.', 2, 'COMP-02', TRUE),
('FAC-05', 'Delegación y Seguimiento', 'Asignación efectiva de tareas según capacidades.', 1, 'COMP-03', TRUE);

-- =============================================================================
-- 4. OPCIONES DE RESPUESTA Y VALORES (CU 24, CU 29)
-- =============================================================================
INSERT INTO opcion_respuesta (codigo, nombre, descripcion, activa) VALUES
('OPC-FREC', 'Escala de Frecuencia (4 niveles)', 'Mide la frecuencia con la que se realiza una acción.', TRUE),
('OPC-ACUE', 'Escala de Acuerdo (4 niveles)', 'Mide el grado de acuerdo ante una afirmación.', TRUE);

-- Valores para OPC-FREC (IDs 1 a 4)
INSERT INTO valor_opcion (id, codigo_opcion, orden, descripcion, activo) VALUES
(1, 'OPC-FREC', 1, 'Nunca', TRUE),
(2, 'OPC-FREC', 2, 'Rara vez', TRUE),
(3, 'OPC-FREC', 3, 'Frecuentemente', TRUE),
(4, 'OPC-FREC', 4, 'Siempre', TRUE);

-- Valores para OPC-ACUE (IDs 5 a 8)
INSERT INTO valor_opcion (id, codigo_opcion, orden, descripcion, activo) VALUES
(5, 'OPC-ACUE', 1, 'Totalmente en desacuerdo', TRUE),
(6, 'OPC-ACUE', 2, 'En desacuerdo', TRUE),
(7, 'OPC-ACUE', 3, 'De acuerdo', TRUE),
(8, 'OPC-ACUE', 4, 'Totalmente de acuerdo', TRUE);

SELECT setval('valor_opcion_id_seq', 8, true);

-- =============================================================================
-- 5. PREGUNTAS (Mínimo 2 activas por cada Factor, suma de ponderaciones = 10)
-- =============================================================================
INSERT INTO pregunta (id, nombre, enunciado, descripcion, tipo_pregunta, codigo_factor, codigo_opcion, activa) VALUES
-- Factor FAC-01 (Comunicación Interpersonal)
(1, 'Escucha en reuniones', '¿Con qué frecuencia espera a que sus compañeros terminen de hablar antes de dar su opinión?', 'Evalúa escucha activa', 'SINGLE_CHOICE', 'FAC-01', 'OPC-FREC', TRUE),
(2, 'Claridad al explicar', '¿Con qué frecuencia verifica que su interlocutor comprendió el mensaje transmitido?', 'Evalúa retroalimentación', 'SINGLE_CHOICE', 'FAC-01', 'OPC-FREC', TRUE),

-- Factor FAC-02 (Cooperación)
(3, 'Apoyo ante sobrecarga', 'Cuando un compañero está atrasado con su tarea, me ofrezco a ayudarlo.', 'Evalúa solidaridad en equipo', 'SINGLE_CHOICE', 'FAC-02', 'OPC-ACUE', TRUE),
(4, 'Documentación compartida', '¿Con qué frecuencia comparte hallazgos o soluciones útiles con el resto del equipo?', 'Evalúa transferencia de conocimiento', 'SINGLE_CHOICE', 'FAC-02', 'OPC-FREC', TRUE),

-- Factor FAC-03 (Pensamiento Analítico)
(5, 'Identificación de causa raíz', '¿Con qué frecuencia investiga la causa raíz de un error antes de aplicar un parche rápido?', 'Evalúa diagnóstico técnico', 'SINGLE_CHOICE', 'FAC-03', 'OPC-FREC', TRUE),
(6, 'División de problemas complejos', 'Ante un requerimiento grande, prefiero dividirlo en módulos pequeños antes de programar.', 'Evalúa descomposición lógica', 'SINGLE_CHOICE', 'FAC-03', 'OPC-ACUE', TRUE),

-- Factor FAC-04 (Toma de Decisiones)
(7, 'Evaluación de riesgos', '¿Con qué frecuencia analiza pros y contras antes de elegir una arquitectura o herramienta?', 'Evalúa criterio técnico', 'SINGLE_CHOICE', 'FAC-04', 'OPC-FREC', TRUE),
(8, 'Decisión bajo presión', 'Puedo priorizar tareas críticas rápidamente cuando surge un incidente en producción.', 'Evalúa resolución bajo presión', 'SINGLE_CHOICE', 'FAC-04', 'OPC-ACUE', TRUE),

-- Factor FAC-05 (Delegación y Seguimiento)
(9, 'Asignación según fortalezas', '¿Con qué frecuencia asigna tareas considerando las habilidades de cada integrante?', 'Evalúa delegación efectiva', 'SINGLE_CHOICE', 'FAC-05', 'OPC-FREC', TRUE),
(10, 'Seguimiento constructivo', 'Realizo revisiones periódicas de avance sin caer en la microgestión.', 'Evalúa seguimiento de equipo', 'SINGLE_CHOICE', 'FAC-05', 'OPC-ACUE', TRUE);

SELECT setval('pregunta_id_seq', 10, true);

-- Ponderaciones por pregunta (Regla de negocio obligatoria: la suma de cada pregunta es exactamente 10.00)
-- Distribución usada en cada pregunta: 0.00 + 1.00 + 3.00 + 6.00 = 10.00
INSERT INTO pregunta_valor_opcion (id_pregunta, id_valor_opcion, ponderacion) VALUES
-- Pregunta 1 (OPC-FREC: valores 1..4)
(1, 1, 0.00), (1, 2, 1.00), (1, 3, 3.00), (1, 4, 6.00),
-- Pregunta 2 (OPC-FREC: valores 1..4)
(2, 1, 0.00), (2, 2, 1.00), (2, 3, 3.00), (2, 4, 6.00),
-- Pregunta 3 (OPC-ACUE: valores 5..8)
(3, 5, 0.00), (3, 6, 1.00), (3, 7, 3.00), (3, 8, 6.00),
-- Pregunta 4 (OPC-FREC: valores 1..4)
(4, 1, 0.00), (4, 2, 1.00), (4, 3, 3.00), (4, 4, 6.00),
-- Pregunta 5 (OPC-FREC: valores 1..4)
(5, 1, 0.00), (5, 2, 1.00), (5, 3, 3.00), (5, 4, 6.00),
-- Pregunta 6 (OPC-ACUE: valores 5..8)
(6, 5, 0.00), (6, 6, 1.00), (6, 7, 3.00), (6, 8, 6.00),
-- Pregunta 7 (OPC-FREC: valores 1..4)
(7, 1, 0.00), (7, 2, 1.00), (7, 3, 3.00), (7, 4, 6.00),
-- Pregunta 8 (OPC-ACUE: valores 5..8)
(8, 5, 0.00), (8, 6, 1.00), (8, 7, 3.00), (8, 8, 6.00),
-- Pregunta 9 (OPC-FREC: valores 1..4)
(9, 1, 0.00), (9, 2, 1.00), (9, 3, 3.00), (9, 4, 6.00),
-- Pregunta 10 (OPC-ACUE: valores 5..8)
(10, 5, 0.00), (10, 6, 1.00), (10, 7, 3.00), (10, 8, 6.00);

-- =============================================================================
-- 6. PUESTOS Y SUS COMPETENCIAS REQUERIDAS (CU 09, CU 25)
-- =============================================================================
INSERT INTO puesto (codigo, nombre, descripcion, empresa, activo) VALUES
('PUE-DEV-JR', 'Desarrollador Backend Junior', 'Desarrollo y mantenimiento de servicios en Java y Spring Boot.', 'TechSantaFe S.A.', TRUE),
('PUE-TL-SR', 'Tech Lead Senior', 'Coordinación técnica de células de desarrollo de software.', 'TechSantaFe S.A.', TRUE);

INSERT INTO puesto_competencia (codigo_puesto, codigo_competencia, ponderacion_minima) VALUES
('PUE-DEV-JR', 'COMP-01', 4.00),
('PUE-DEV-JR', 'COMP-02', 5.00),
('PUE-TL-SR', 'COMP-01', 5.00),
('PUE-TL-SR', 'COMP-02', 5.00),
('PUE-TL-SR', 'COMP-03', 5.00);

