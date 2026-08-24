# SPRINT 01 — Product Definition & First Screen

## Goal

Define the initial product concept, create the first formal feature
specification, and implement the first user interface using Kotlin and
Jetpack Compose.

This Sprint introduces the course development cycle:

**SPEC → BUILD → VALIDATE → EXPLAIN → COMMIT**

---

## Sprint Goal

By the end of this Sprint, each team must have:

1. a clearly defined mobile application concept;
2. the first feature specification (`SPEC-001`);
3. a functional first screen implemented with Jetpack Compose;
4. evidence that the application runs successfully;
5. a Pull Request submitted for instructor review.

---

## 1. Product Definition

Before writing code, define the product that your team intends to
develop throughout the course.

Create the section `Product Definition` inside `SPRINT-01.md` and
describe:

### Product Name

What is the name of the application?

### Problem

What problem does the application address?

### Target Users

Who are the intended users?

### Product Goal

What should the application help users accomplish?

### Initial Features

List the main features currently envisioned for the application.

Example:

- user onboarding;
- home screen;
- item registration;
- item listing;
- detailed item view;
- local data persistence.

These features are preliminary and may evolve during future Sprints.

---

## 2. Create SPEC-001

Create:

`projects/team-XX/docs/specs/SPEC-001.md`

Use the course specification template available at:

`templates/SPEC-TEMPLATE.md`

The specification must describe the first screen of the application.

---

## 3. Minimum Requirements for SPEC-001

The specification must contain:

- Context
- Task
- Functional Requirements
- Constraints
- Acceptance Criteria
- Validation
- AI Usage
- Out of Scope
- Deliverables

---

## 4. First Screen

Implement the first screen of the application using:

- Kotlin;
- Jetpack Compose.

The screen must contain at least:

- application name;
- short application description or slogan;
- one primary action button;
- appropriate layout organization;
- spacing and alignment.

Students should use Compose components such as:

- `Column`;
- `Row` when appropriate;
- `Text`;
- `Button`;
- `Spacer`;
- `Modifier`.

Additional components may be used when justified.

---

## 5. Scope

The purpose of this Sprint is the implementation of the first user
interface.

The following are NOT required yet:

- multiple-screen navigation;
- database;
- authentication;
- external APIs;
- cloud services;
- advanced architecture;
- complete application functionality.

These topics will be addressed progressively in future Sprints.

---

## 6. Acceptance Criteria

- AC-01 — The application has a clearly defined name.
- AC-02 — The problem addressed by the application is documented.
- AC-03 — The target users are identified.
- AC-04 — The initial product goal is documented.
- AC-05 — `SPEC-001.md` exists in the required directory.
- AC-06 — SPEC-001 contains functional requirements.
- AC-07 — SPEC-001 contains measurable acceptance criteria.
- AC-08 — The first screen is implemented using Jetpack Compose.
- AC-09 — The application name is visible on the screen.
- AC-10 — A description or slogan is visible.
- AC-11 — At least one primary action button is present.
- AC-12 — The application builds successfully.
- AC-13 — The application runs without crashing.
- AC-14 — The implemented screen satisfies SPEC-001.
- AC-15 — The team can explain the implementation.

---

## 7. Validation

The team must:

1. build the application;
2. run the application on an Android Emulator or physical device;
3. compare the implemented screen with SPEC-001;
4. verify each acceptance criterion;
5. capture evidence of the running application.

---

## 8. Evidence

Create:

`projects/team-XX/evidence/sprint-01/`

Include at least:

`welcome-screen.png`

The screenshot must clearly show the implemented application running.

---

## 9. AI Usage

AI-assisted development tools may be used.

The team must document:

- which AI tool was used;
- how it was used;
- what content was generated or suggested;
- what was manually reviewed or modified;
- how the generated solution was validated.

Remember:

**AI may generate. The student must understand, validate, and explain.**

---

## 10. Deliverables

The Pull Request must contain:

- `projects/team-XX/app/`
- `projects/team-XX/SPRINT-01.md`
- `projects/team-XX/docs/specs/SPEC-001.md`
- `projects/team-XX/evidence/sprint-01/welcome-screen.png`

---

## 11. Suggested Branch Name

`team-XX/sprint-01`

Example:

`team-03/sprint-01`

---

## 12. Suggested Commit Messages

Examples:

`docs: define product and SPEC-001`

`feat: implement initial application screen`

`docs: add sprint 01 validation evidence`

---

## 13. Pull Request

Use the title:

`[Sprint 01] Team XX — Product Definition & First Screen`

The Pull Request must target:

`main`

of the official course repository.

Students must not merge their own Pull Requests.

---

## Definition of Done

Sprint 01 is considered submitted when:

- the product concept is documented;
- SPEC-001 is complete;
- the first screen is implemented;
- the application runs successfully;
- evidence is included;
- the implementation can be explained by the team;
- a Pull Request has been submitted for instructor review.