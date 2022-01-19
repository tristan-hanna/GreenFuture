import Hardware
import time
from firebase import firebase

firebase = firebase.FirebaseApplication('https://green-future-cc3e6-default-rtdb.firebaseio.com/', None)

#RELAY = Hardware.Relay(18, False)
#SECONDS_TO_WATER = 10

#def water_plant(relay, seconds):
def water_plant(relay, percent):

	data3 = []
	if percent > 25:
		relay.on()
	else:
		relay.off()
		plant_time = int(time.time())
		data3.append(plant_time)
		data3 = {"plantTime": plant_time}
		firebase.patch('Set_Time', data3)

#def main():
#       	water_plant(RELAY, SECONDS_TO_WATER)
#	print("\nPlant was last watered at {}".format(time_keeper.time_last_watered))

#while True:
#	time.sleep(SECONDS_TO_WATER)
#	main()
