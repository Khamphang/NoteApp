# Note Taker — Android App

> ⚗️ **ແອັບນີ້ສ້າງຂຶ້ນເພື່ອທົດລອງໃຊ້ AI (Claude) ໃນການພັດທະນາ Android app ແບບ end-to-end**
> ທົດລອງວ່າ AI ສາມາດອອກແບບ, ຂຽນ code, debug, ແລະ ສ້າງແອັບທີ່ใช้งານໄດ້ຈິງໂດຍ human ບໍ່ຕ້ອງຂຽນ code ດ້ວຍຕົນເອງ

---

## ກ່ຽວກັບໂປຣເຈັກ

Note-taking app ສຳລັບ Android ທີ່ເຮັດວຽກໄດ້ຈິງ ທົດສອບໃຊ້ງານໃນ HUAWEI PAR-LX9
ໂນດທຸກຢ່າງຖືກ encrypt ໃນ Android Keystore — ຂໍ້ມູນຢູ່ໃນເຄື່ອງ, ບໍ່ສົ່ງ server

## ຂະບວນການ AI ທີ່ໃຊ້

ໂປຣເຈັກນີ້ຜ່ານ multi-agent pipeline:

1. **ສະພາ (Six Hats Council)** — AI 5 ບົດບາດວິເຄາະ requirements ຮ່ວມກັນ ຫຼັງຈາກນັ້ນ Blue Hat ສ້າງ Markdown Brief
2. **Superpowers Pipeline (7 ຂັ້ນຕອນ)**:
   - Clarify & Confirm
   - Worktree / Environment Setup
   - Break into Tasks (TodoWrite)
   - Execute — **2 parallel AI agents** ຂຽນ code ພ້ອມກັນ (Agent A: data layer, Agent B: UI layer)
   - TDD cycle (RED → GREEN → REFACTOR)
   - Self Code Review
   - Handoff
3. **Council Review** — 3-Hat check (Black/Yellow/White) ກ່ອນ approve

Human ເຮັດແຕ່: approve brief, ເບິ່ງ build errors, ທົດສອບໃນມືຖື

## Features

- ບັນທຶກ / ລົບ / ຄົ້ນຫາໂນດ
- Export JSON
- AES-256-GCM encryption ຜ່ານ Android Keystore
- BiometricPrompt (ລາຍນີ້ມື / PIN) lock screen
- Adaptive launcher icon

## Stack

- Kotlin 2.0.0
- Jetpack Compose + Material3
- EncryptedSharedPreferences (Android Security Crypto)
- BiometricPrompt
- ViewModel + StateFlow
- minSdk 26 (Android 8.0+), targetSdk 35

## Build

ເປີດໃນ Android Studio → Sync → Run

> `gradle-wrapper.jar` ຈະຖືກ download ໂດຍອັດຕະໂນມັດ ໃນເທື່ອທຳອິດ

## ສິ່ງທີ່ຮຽນໄດ້ຈາກການທົດລອງ

- AI agents ສາມາດຂຽນ code ທີ່ compile ແລະ run ໄດ້ຈິງ
- Parallel agents ຕ້ອງ split ງານດ້ວຍ file boundary ເພື່ອຫຼີກລ້ຽງ conflict
- Human ຍັງຕ້ອງ approve design, ຕວດ errors, ແລະ ທົດສອບໃນອຸປະກອນຈິງ
- Pipeline ຊ່ວຍລົດ time-to-working-app ລົງຢ່າງຫຼວງຫຼາຍ

---

*Built with [Claude Code](https://claude.ai/claude-code) — AI-driven parallel agent experiment*
