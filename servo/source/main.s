@==========================================
@
@
@==========================================
.section .init
.globl _start
_start:

b main
@======================================MACROS==========================================
@==========================================
@R0: Numero de pin GPIO
@R1: Lectura/Escritura
@Asignacion de funcion al pin (Lectura/Escritura)
@==========================================
.macro SetFuncion pin, valor
	MOV R0, \pin
	MOV R1, \valor
	Bl SetGpioFunction
.endm

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
@========================================================================================

@==========================================
@BOTON1 -> GPIO 7
@BOTON2 -> GPIO 8
@BOTON3 -> GPIO 25
@BOTON4 -> GPIO 24
@BOTON5 -> GPIO 23
@BOTON6 -> GPIO 18
@SERVO  -> GPIO 11
@LED1   -> GPIO 10
@LED2   -> GPIO 9
@==========================================
.section .text
main:
	mov sp, #0x8000

	PULSO_A 	.req R6 			@Tiempo pulso alto
	PULSO_B 	.req R7 			@Tiempo pulso bajo
	CONTADOR 	.req R8 			@Contador para guardar y reproducir secuencia
	ORDEN 		.req R9 			@Vector donde se guarda secuancia
	ACCION		.req R10 			@Numero del boton precionado

	@=====Asignacion de funcion a los pines=====
	SetFuncion #7, #0
	SetFuncion #8, #0
	SetFuncion #25, #0
	SetFuncion #24, #0
	SetFuncion #23, #0
	SetFuncion #18, #0
	SetFuncion #11, #1
	SetFuncion #10, #1
	SetFuncion #9, #1


	LDR PULSO_A, =500 				@Tiempo pulso alto para 0 grados 
	LDR PULSO_B, =24500 			@Tiempo pulso bajo para 0 grados
	# LDR ORDEN, 	 =orden 			@Carga de direccion del vector donde se guarda la secuencia
	MOV R11, #0 					@Se mueve un 4 para multiplicar por el contador 

	@=====Direccion GPIO base=====
	bl GetGpioAddress
	mov r4,r0

@==========================================================
@Ciclo infinito en donde se evalua el estado de los botones
@==========================================================
SELECCION_BOTON:
	MOV CONTADOR, #0 					@Inicio del contador en 0
	POSICION #11, PULSO_A, PULSO_B 		@Mover servo "n" grados
	MOV ACCION, #0 						@Mueve 0 a accion

	GetGPIO #25 						@Analiza estado del boton 25
	# MOVNE ACCION, #3 					@Si Esta presionado Accion = 2
	BEQ SELECCION_BOTON

	GetGPIO #7  						@Analiza estado del boton 1
	MOVNE ACCION, #1 					@Si Esta presionado Accion = 1

	GetGPIO #8 							@Analiza estado del boton 2
	MOVNE ACCION, #2 					@Si Esta presionado Accion = 2

	CMP ACCION, #0 						@Compara Accion con 0
	BLNE POSICIONES 					@Si se presiono algun boton del 1-2 salta a la subrutina para cambiar posicion del servo

	B SELECCION_BOTON 					@Repite ciclo infinito
