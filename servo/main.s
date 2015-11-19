@=============ARREGLO================
.section .init
.globl _start
_start:

b main
@=========================================
@=========================================
.macro SetFuncion pin, valor
	MOV R0, \pin
	MOV R1, \valor
	Bl SetGpioFunction
.endm

.macro SetPIN puerto, valor
	MOV R0, \puerto
	MOV R1, \valor
	BL SetGpio
.endm

.macro GetGPIO boton
	LDR R5, [R4, #0x34]
	MOV R0, #1
	LSL R0, \boton
	AND R5, R0
	TEQ R5, #0
.endm

.macro POSICION pin, alto, bajo
	SetPIN \pin, #1
	MOV R0, \alto
	BL Wait
	SetPIN \pin, #0
	MOV R0, \bajo
	BL Wait
.endm


@=========================================
@=========================================
@BOTON1 -> GPIO 7
@BOTON2 -> GPIO 8
@BOTON3 -> GPIO 25
@BOTON4 -> GPIO 24
@BOTON5 -> GPIO 23
@BOTON6 -> GPIO 18
@SERVO  -> GPIO 11
@LED    -> GPIO 9
@=========================================
@=========================================
.section .text
main:
	mov sp, #0x8000

	PULSO_A 	.req R6
	PULSO_B 	.req R7
	CONTADOR 	.req R8
	ORDEN 		.req R9

	SetFuncion #7, #0
	SetFuncion #8, #0
	SetFuncion #25, #0
	SetFuncion #24, #0
	SetFuncion #23, #0
	SetFuncion #17, #1
	SetFuncion #11, #1
	SetFuncion #9, #1

	/* Direccion GPIO base */
	bl GetGpioAddress
	mov r4,r0

	
	MOV R11, #4
	LDR ORDEN, =orden

SELECCIONAR:
	SetPIN #9, #0
	SetPIN #11, #0
	SetPIN #17, #0
	MOV CONTADOR, #0

	GetGPIO #23
	BNE INFINITO

	GetGPIO #24
	BNE REPRODUCIR

	B SELECCIONAR
	


INFINITO:
	SetPIN #9, #1
	SetPIN #11, #0
	SetPIN #17, #0
	CMP CONTADOR, #3
	MOVEQ CONTADOR, #0
	BEQ SELECCIONAR

	GetGPIO #7
	BNE CAMBIO_1

	GetGPIO #8
	BNE CAMBIO_2

	GetGPIO #25
	BNE CAMBIO_3

	# GetGPIO #24
	# MOVNE CONTADOR, #0
	# BNE REPRODUCIR

	B INFINITO

CAMBIO_1:
	SetPIN #17, #1
	LDR R0, =500000
	BL Wait

	MUL R1, CONTADOR, R11
	MOV R0, #1
	STR R0, [ORDEN, R1]
	ADD CONTADOR, #1
	B INFINITO

CAMBIO_2:
	SetPIN #17, #1
	LDR R0, =500000
	BL Wait

	MUL R1, CONTADOR, R11
	MOV R0, #2
	STR R0, [ORDEN, R1]
	ADD CONTADOR, #1
	B INFINITO

CAMBIO_3:
	SetPIN #17, #1
	LDR R0, =500000
	BL Wait

	MUL R1, CONTADOR, R11
	MOV R0, #3
	STR R0, [ORDEN, R1]
	ADD CONTADOR, #1
	B INFINITO

REPRODUCIR:
	SetPIN #11, #0
	SetPIN #17, #0
	MOV R5, #80
	CMP CONTADOR, #3
	BEQ TODOS

	MUL R1, CONTADOR, R11
	LDR R10, [ORDEN, R1]
	CMP R10, #1
	BEQ PRENDER_1
	CMP R10, #2
	BEQ PRENDER_2
	CMP R10, #3
	BEQ PRENDER_3

@===================================
PRENDER_1:
	SetPIN #11, #1
	LDR R0, =500
	BL Wait

	SetPIN #11, #0
	LDR R0, =24500
	BL Wait

	SUBS R5, #1
	BNE PRENDER_1

	ADD CONTADOR, #1
	B REPRODUCIR

PRENDER_2:
	SetPIN #11, #1
	LDR R0, =1000
	BL Wait

	SetPIN #11, #0
	LDR R0, =24000
	BL Wait

	SUBS R5, #1
	BNE PRENDER_2

	ADD CONTADOR, #1
	B REPRODUCIR

PRENDER_3:
	SetPIN #11, #1
	LDR R0, =1500
	BL Wait

	SetPIN #11, #0
	LDR R0, =23500
	BL Wait

	SUBS R5, #1
	BNE PRENDER_3

	ADD CONTADOR, #1
	B REPRODUCIR
@===================================

TODOS:
	SetPIN #11, #1
	SetPIN #9, #1
	SetPIN #17, #1
	LDR R0, =500000
	BL Wait

	B SELECCIONAR


.section .data
.align 2
orden:		.word 1,1,1
