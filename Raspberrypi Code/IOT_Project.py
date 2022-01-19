import RPi.GPIO as GPIO
import PCF8591 as ADC
import DHT11 as DHT
import Hardware
import test_relay as relay
import time
from firebase import firebase

GPIO.cleanup()
GPIO.setwarnings(False)

RELAY = Hardware.Relay(18, False)

firebase = firebase.FirebaseApplication('https://green-future-cc3e6-default-rtdb.firebaseio.com/', None)

def setup():
	ADC.setup(0x48)

def loop():
	while True:
		data = []
		data2 = []
		data3 = []

		adcOut = ADC.read(0)
		percent = ((120 - adcOut) / 25) * 100
		data.append(percent)
		database_moisture = percent

		relay.water_plant(RELAY, percent)

		cur_time = int(time.time())
		data.append(cur_time)
		database_time = cur_time

		TH_result = DHT.read_dht11_dat()
		if TH_result:
			humidity, temperature = TH_result
			print ("humidity: %s %%,  Temperature: %s C" % (humidity, temperature))
			data2.append(TH_result)
			data2 = {"m_time": database_time, "temperature": temperature, "humidity": humidity}
			firebase.patch('Set_TH', data2)

		data = {"m_time": database_time, "moisture": database_moisture}
		print("ADC Output: {} Percentage: {:.2f}%".format (adcOut, percent))

		ADC.write(ADC.read(0))

		firebase.patch('Set_Moisture', data)
		firebase.post('PCF', data)
		firebase.post('DHT', data2)

def destroy():
	ADC.write(0)

if __name__ == "__main__":
	try:
		setup()
		loop()
	except KeyboardInterrupt:
		destroy()
