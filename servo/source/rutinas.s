@======================================MACROS==========================================
@==========================================
@R0: Numero de pin GPIO
@R1: Encendido/Apagado
@Encender o apagar el pin
@==========================================
.macro SetPIN puerto, valor
	MOV R0, \puerto
	MOV R1, \valor
	BL SetGpio
.endm

@==========================================
@R0: Numero de pin GPIO
@R1: Encendido/Apagado
@Encender o apagar el pin si se presiona algun boton
@==========================================
.macro SetPINDiferente puerto, valor
	MOVNE R0, \puerto
	MOVNE R1, \valor
	BLNE SetGpio
.endm

@==========================================
@R4: Direccion base del programa
@R5: Direccion donde se revisa el nivel del pin(Alto/Bajo)
@Verifica si se presiono un boton
@==========================================
.macro GetGPIO boton
	LDR R5, [R4, #0x34]
	MOV R0, #1
	LSL R0, \boton
	AND R5, R0
	TEQ R5, #0
.endm

@==========================================
@Verifica si algun boton se presiono 
@Si algun boton esta presionado suma 1 a contador
@Cambia el valor de accion dependiendo del boton
@Enciende pin si boton esta presionado
@==========================================
.macro setAccion boton, numero
	GetGPIO \boton
	ADDNE CONTADOR, #1
	MOVNE ACCION, \numero
	SetPINDiferente #10, #1
.endm

@==========================================
@R0: Tiempo pulso (alto/bajo)
@Mueve el servo a cierta posicion
@==========================================
.macro POSICION pin, alto, bajo
	SetPIN \pin, #1
	MOV R0, \alto
	BL Wait
	SetPIN \pin, #0
	MOV R0, \bajo
	BL Wait
.endm
@======================================
@======================================================================================

PULSO_A 	.req R6 			@Tiempo pulso alto
PULSO_B 	.req R7 			@Tiempo pulso bajo
CONTADOR 	.req R8 			@Contador para guardar y reproducir secuencia
# ORDEN 		.req R9 			@Vector donde se guarda secuancia
ACCION		.req R10 			@Numero del boton precionado

@======================================
@Posicionar servo a 0 grados
@======================================
.globl POSICION_0
POSICION_0:
	CMP R11, #0
	LDREQ PULSO_A, =500
	LDREQ PULSO_B, =24500
	MOV PC, LR

@======================================
@Posicionar servo a 180 grados
@======================================
.globl POSICION_180
POSICION_180:
	CMP R11, #0
	LDREQ PULSO_A, =2500
	LDREQ PULSO_B, =22500
	MOV PC, LR

.globl CONDICION
CONDICION:
	PUSH {LR}
	CMP R11, #0
	MOVEQ R11, #1
	MOVNE R11, #0
	LDR R0, =500000
	BL Wait
	POP {PC}

@==================================================
@Verifica que boton se presiono para mover el servo
@==================================================
.globl POSICIONES
POSICIONES:
	PUSH {LR}
	CMP ACCION, #1   								@Verifica si se presiono boton 1
	BLEQ POSICION_0 								@Si se presiono mueve a posicion 1
	CMP ACCION, #2 									@Verifica si se presiono boton 2
	BLEQ POSICION_180 								@Si se presiono mueve a posicion 2
	CMP ACCION, #3
	BLEQ CONDICION
	POP {PC}
