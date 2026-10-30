-- =============================================================================
-- 1. USUARIOS Y CANDIDATOS
-- =============================================================================

-- Tabla de consultores (RRHH) que administran el sistema (CU 01)
CREATE TABLE IF NOT EXISTS usuario_consultor (
    id              SERIAL       PRIMARY KEY,
    nombre_usuario  VARCHAR(50)  NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    nombre          VARCHAR(100) NOT NULL,
    apellido        VARCHAR(100) NOT NULL,
    email           VARCHAR(150) NOT NULL UNIQUE
);

-- Tabla de candidatos que rinden los cuestionarios (CU 01, CU 06)
CREATE TABLE IF NOT EXISTS candidato (
    nro_candidato   INTEGER      PRIMARY KEY,
    apellido        VARCHAR(100) NOT NULL,
    nombre          VARCHAR(100) NOT NULL,
    tipo_doc        VARCHAR(10)  NOT NULL CHECK (tipo_doc IN ('DNI', 'LE', 'LC', 'PP')),
    nro_doc         VARCHAR(20)  NOT NULL,
    fecha_nac       DATE,
    genero          VARCHAR(5)   CHECK (genero IN ('H', 'M')),
    nacionalidad    VARCHAR(80),
    email           VARCHAR(150),
    escolaridad     VARCHAR(100),
    eliminado       BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT uq_candidato_documento UNIQUE (tipo_doc, nro_doc)
);

-- =============================================================================
-- 2. CATÁLOGO DE COMPETENCIAS, FACTORES Y PUESTOS
-- =============================================================================

-- Tabla de competencias evaluables (CU 13)
CREATE TABLE IF NOT EXISTS competencia (
    codigo          VARCHAR(30)  PRIMARY KEY,
    nombre          VARCHAR(120) NOT NULL UNIQUE,
    descripcion     TEXT,
    tipo            VARCHAR(20)  NOT NULL CHECK (tipo IN ('TECNICA', 'ACTITUDINAL')),
    activa          BOOLEAN      NOT NULL DEFAULT TRUE
);

-- Tabla de factores que componen una competencia (CU 17)
CREATE TABLE IF NOT EXISTS factor (
    codigo              VARCHAR(30)  PRIMARY KEY,
    nombre              VARCHAR(120) NOT NULL,
    descripcion         TEXT,
    nro_orden           INTEGER      NOT NULL CHECK (nro_orden > 0),
    codigo_competencia  VARCHAR(30)  NOT NULL REFERENCES competencia(codigo) ON DELETE RESTRICT,
    activo              BOOLEAN      NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_factor_orden_por_competencia UNIQUE (codigo_competencia, nro_orden)
);

-- Tabla de puestos o funciones laborales (CU 09)
CREATE TABLE IF NOT EXISTS puesto (
    codigo          VARCHAR(30)  PRIMARY KEY,
    nombre          VARCHAR(120) NOT NULL UNIQUE,
    descripcion     TEXT,
    empresa         VARCHAR(120) NOT NULL,
    activo          BOOLEAN      NOT NULL DEFAULT TRUE
);

-- Tabla intermedia entre puesto y competencia con la nota mínima requerida (0 a 10)
CREATE TABLE IF NOT EXISTS puesto_competencia (
    codigo_puesto       VARCHAR(30)    NOT NULL REFERENCES puesto(codigo) ON DELETE CASCADE,
    codigo_competencia  VARCHAR(30)    NOT NULL REFERENCES competencia(codigo) ON DELETE RESTRICT,
    ponderacion_minima  NUMERIC(4, 2)  NOT NULL CHECK (ponderacion_minima >= 0 AND ponderacion_minima <= 10),
    PRIMARY KEY (codigo_puesto, codigo_competencia)
);

-- =============================================================================
-- 3. OPCIONES DE RESPUESTA Y PREGUNTAS
-- =============================================================================

-- Tabla de grupos/tipos de opciones de respuesta (CU 24, CU 29)
CREATE TABLE IF NOT EXISTS opcion_respuesta (
    codigo          VARCHAR(30)  PRIMARY KEY,
    nombre          VARCHAR(120) NOT NULL UNIQUE,
    descripcion     TEXT,
    activa          BOOLEAN      NOT NULL DEFAULT TRUE
);

-- Tabla de valores posibles dentro de una opción de respuesta (ej: Nunca, A veces, Siempre)
CREATE TABLE IF NOT EXISTS valor_opcion (
    id              SERIAL       PRIMARY KEY,
    codigo_opcion   VARCHAR(30)  NOT NULL REFERENCES opcion_respuesta(codigo) ON DELETE CASCADE,
    orden           INTEGER      NOT NULL CHECK (orden > 0),
    descripcion     VARCHAR(255) NOT NULL,
    activo          BOOLEAN      NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_valor_opcion_orden UNIQUE (codigo_opcion, orden)
);

-- Tabla de preguntas de evaluación asociadas a un factor (CU 20, CU 21, CU 22, CU 23)
CREATE TABLE IF NOT EXISTS pregunta (
    id              SERIAL       PRIMARY KEY,
    nombre          VARCHAR(150) NOT NULL UNIQUE,
    enunciado       TEXT         NOT NULL,
    descripcion     TEXT,
    tipo_pregunta   VARCHAR(30)  NOT NULL DEFAULT 'SINGLE_CHOICE' CHECK (tipo_pregunta IN ('SINGLE_CHOICE')),
    codigo_factor   VARCHAR(30)  NOT NULL REFERENCES factor(codigo) ON DELETE RESTRICT,
    codigo_opcion   VARCHAR(30)  NOT NULL REFERENCES opcion_respuesta(codigo) ON DELETE RESTRICT,
    activa          BOOLEAN      NOT NULL DEFAULT TRUE
);

-- Tabla intermedia que asigna el puntaje (ponderación) de cada valor de respuesta para una pregunta (suma = 10)
CREATE TABLE IF NOT EXISTS pregunta_valor_opcion (
    id_pregunta     INTEGER       NOT NULL REFERENCES pregunta(id) ON DELETE CASCADE,
    id_valor_opcion INTEGER       NOT NULL REFERENCES valor_opcion(id) ON DELETE RESTRICT,
    ponderacion     NUMERIC(4, 2) NOT NULL CHECK (ponderacion >= 0 AND ponderacion <= 10),
    PRIMARY KEY (id_pregunta, id_valor_opcion)
);

-- =============================================================================
-- 4. EVALUACIONES Y CUESTIONARIOS
-- =============================================================================

-- Tabla cabecera de una evaluación para un puesto (CU 25)
CREATE TABLE IF NOT EXISTS evaluacion (
    id              SERIAL      PRIMARY KEY,
    codigo_puesto   VARCHAR(30) NOT NULL REFERENCES puesto(codigo) ON DELETE RESTRICT,
    fecha_creacion  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activa          BOOLEAN     NOT NULL DEFAULT TRUE
);

-- Tabla de cuestionarios asignados a cada candidato evaluado (CU 25, CU 26, CU 32)
CREATE TABLE IF NOT EXISTS cuestionario (
    id                SERIAL       PRIMARY KEY,
    id_evaluacion     INTEGER      NOT NULL REFERENCES evaluacion(id) ON DELETE CASCADE,
    nro_candidato     INTEGER      NOT NULL REFERENCES candidato(nro_candidato) ON DELETE RESTRICT,
    clave             VARCHAR(8)   NOT NULL UNIQUE,
    estado            VARCHAR(20)  NOT NULL DEFAULT 'ACTIVO'
                      CHECK (estado IN ('ACTIVO', 'EN_PROCESO', 'COMPLETO', 'INCOMPLETO', 'SIN_CONTESTAR')),
    fecha_creacion    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_inicio      TIMESTAMP,
    fecha_fin         TIMESTAMP,
    ultimo_ingreso    TIMESTAMP,
    cantidad_accesos  INTEGER      NOT NULL DEFAULT 0 CHECK (cantidad_accesos >= 0)
);

-- Tabla de bloques (pantallas del wizard) en los que se divide un cuestionario (CU 26)
CREATE TABLE IF NOT EXISTS bloque_cuestionario (
    id              SERIAL    PRIMARY KEY,
    id_cuestionario INTEGER   NOT NULL REFERENCES cuestionario(id) ON DELETE CASCADE,
    nro_bloque      INTEGER   NOT NULL CHECK (nro_bloque > 0),
    contestado      BOOLEAN   NOT NULL DEFAULT FALSE,
    CONSTRAINT uq_bloque_por_cuestionario UNIQUE (id_cuestionario, nro_bloque)
);

-- Tabla de preguntas sorteadas para un cuestionario y la respuesta elegida por el candidato (CU 26)
CREATE TABLE IF NOT EXISTS pregunta_seleccionada (
    id               SERIAL    PRIMARY KEY,
    id_cuestionario  INTEGER   NOT NULL REFERENCES cuestionario(id) ON DELETE CASCADE,
    id_bloque        INTEGER   NOT NULL REFERENCES bloque_cuestionario(id) ON DELETE CASCADE,
    id_pregunta      INTEGER   NOT NULL REFERENCES pregunta(id) ON DELETE RESTRICT,
    id_valor_elegido INTEGER   REFERENCES valor_opcion(id) ON DELETE RESTRICT,
    orden            INTEGER   NOT NULL CHECK (orden > 0),
    fecha_hora       TIMESTAMP,
    CONSTRAINT uq_pregunta_en_cuestionario UNIQUE (id_cuestionario, id_pregunta)
);

-- =============================================================================
-- 5. RESULTADOS, AUDITORÍA Y CONFIGURACIÓN
-- =============================================================================

-- Resultados calculados por competencia al finalizar un cuestionario (CU 26, CU 28)
CREATE TABLE IF NOT EXISTS resultado_competencia (
    id                  SERIAL        PRIMARY KEY,
    id_cuestionario     INTEGER       NOT NULL REFERENCES cuestionario(id) ON DELETE CASCADE,
    codigo_competencia  VARCHAR(30)   NOT NULL REFERENCES competencia(codigo) ON DELETE RESTRICT,
    puntaje_obtenido    NUMERIC(5, 2) NOT NULL,
    ponderacion_minima  NUMERIC(4, 2) NOT NULL,
    aprobado            BOOLEAN       NOT NULL,
    CONSTRAINT uq_resultado_competencia UNIQUE (id_cuestionario, codigo_competencia)
);

-- Resultados calculados por factor al finalizar un cuestionario (CU 26, CU 28)
CREATE TABLE IF NOT EXISTS resultado_factor (
    id               SERIAL        PRIMARY KEY,
    id_cuestionario  INTEGER       NOT NULL REFERENCES cuestionario(id) ON DELETE CASCADE,
    codigo_factor    VARCHAR(30)   NOT NULL REFERENCES factor(codigo) ON DELETE RESTRICT,
    puntaje_obtenido NUMERIC(5, 2) NOT NULL,
    CONSTRAINT uq_resultado_factor UNIQUE (id_cuestionario, codigo_factor)
);

-- Tabla de auditoría para bajas lógicas (Patrón Observer - CU 23 y catálogos)
CREATE TABLE IF NOT EXISTS registro_auditoria (
    id          SERIAL       PRIMARY KEY,
    entidad     VARCHAR(80)  NOT NULL,
    id_entidad  VARCHAR(80)  NOT NULL,
    accion      VARCHAR(50)  NOT NULL,
    usuario     VARCHAR(100) NOT NULL,
    fecha_hora  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    detalle     TEXT
);

-- Tabla de auditoría de procesos batch / vencimiento de cuestionarios (CU 32)
CREATE TABLE IF NOT EXISTS registro_ejecucion (
    id                   SERIAL      PRIMARY KEY,
    proceso              VARCHAR(80) NOT NULL DEFAULT 'CIERRE_CUESTIONARIOS_VENCIDOS',
    accion               VARCHAR(20) NOT NULL CHECK (accion IN ('INICIO', 'FIN')),
    fecha_hora           TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cantidad_procesados  INTEGER     DEFAULT 0,
    observaciones        TEXT
);

-- Tabla de parámetros generales para los cuestionarios
CREATE TABLE IF NOT EXISTS configuracion_cuestionario (
    id                      SERIAL    PRIMARY KEY,
    preguntas_por_bloque    INTEGER   NOT NULL DEFAULT 5 CHECK (preguntas_por_bloque > 0),
    tiempo_maximo_minutos   INTEGER   NOT NULL DEFAULT 60 CHECK (tiempo_maximo_minutos > 0),
    maximo_accesos          INTEGER   NOT NULL DEFAULT 3 CHECK (maximo_accesos > 0),
    preguntas_por_factor    INTEGER   NOT NULL DEFAULT 2 CHECK (preguntas_por_factor >= 2)
);
