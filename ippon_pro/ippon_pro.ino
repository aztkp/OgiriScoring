int switch_pin[5], count[5], state[5], stateTmp[5];
int num = 5;

void setup() {
  for (int i=0; i<num; i++) {
    switch_pin[i] = i+2;
    count[i] = 0;
    state[i] = 0;
    stateTmp[i] = 0;
  }
  Serial.begin(38400);
  for (int i=0; i<num; i++) {
    pinMode(switch_pin[i], INPUT);  
  }
}

void loop() {
  for (int i=0; i<num; i++) {
    stateTmp[i] = state[i];
    state[i] = digitalRead(switch_pin[i]);
    if (state[i] == 1 && stateTmp[i] == 0 && count[i] < 2) {
    count[i]++;
    Serial.print(i);
    Serial.print(": ");
    Serial.println(count[i]);
  }
  }
  //stateTmp = state;
  //state = digitalRead(switch_pin);
  //Serial.println(state);
  delay(10);
}
