# Coupon Service

API de cupons (Create/Delete) implementada com Spring Boot 3, JPA, H2 e testes de integração. Foco em regras de negócio e arquitetura limpa (domínio + casos de uso + portas/adapters).

## Requisitos do Desafio
- Create
  - `code` obrigatório, normalizado (remove não-alfanuméricos, uppercase) e com exatamente 6 caracteres após normalização.
  - `description` obrigatório.
  - `discountValue` mínimo 0.5.
  - `expirationDate` não pode estar no passado.
  - Pode vir `published=true` na criação.
- Delete
  - Soft delete (campo `deletedAt`).
  - Não pode deletar duas vezes (2ª tentativa -> 409).

## Arquitetura
- domain/: entidades e regras (ex.: `Coupon`, `CouponCode`, exceções de domínio).
- application/: casos de uso (intenção única) e portas (`CouponRepository`).
- infrastructure/: JPA (entity, repository adapter) e mapeamentos.
- adapters/in/web/: controllers REST e DTOs.
- config/: beans, OpenAPI e exception handler.

## Executando localmente
Pré‑requisitos: Java 17.

- Via Maven Wrapper:
```
./mvnw spring-boot:run   # Linux/macOS
# ou
.\mvnw spring-boot:run  # Windows PowerShell/CMD
```

- Endpoints:
  - POST `/api/v1/coupons`
  - DELETE `/api/v1/coupons/{code}`

- Swagger:
  - UI: http://localhost:8080/swagger-ui/index.html
  - OpenAPI JSON: http://localhost:8080/v3/api-docs

- H2 Console (debug opcional):
  - http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:coupons`, user: `sa`, password: vazio

## Exemplos (curl)
Create:
```
curl -X POST "http://localhost:8080/api/v1/coupons" \
  -H "Content-Type: application/json" \
  -d '{
    "code":"ab-12#c9",
    "description":"10% OFF",
    "discountValue":10.0,
    "expirationDate":"2030-12-31",
    "published":true
  }'
```
Delete:
```
curl -X DELETE "http://localhost:8080/api/v1/coupons/ab-12#c9"
```

## Testes
- Integração com H2 cobrindo regras e anti‑casos (MockMvc).
```
./mvnw test
```

## Docker
- Build e executar com Docker Compose:
```
docker compose up --build
```
- App disponível em `http://localhost:8080`.

## Decisões de Domínio
- `CouponCode`: normaliza o código e exige 6 chars após limpeza (consistência com a regra do enunciado). Unicidade reforçada no caso de uso e com índice único na tabela.
- Soft delete: `deletedAt` controla deleção; 2ª deleção gera `409 Conflict`.

## Licença
Projeto de exemplo para desafio técnico.
