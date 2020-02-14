# keyko-event-streamer

![Web3 event streamer CI](https://github.com/keyko-io/web3-event-streamer/workflows/Java%20CI/badge.svg?branch=master)
[![javadoc](https://javadoc.io/badge2/io.keyko.monitoring/web3-event-streamer/javadoc.svg)](https://javadoc.io/doc/io.keyko.monitoring/web3-event-streamer)

Keyko Web3 framework for processing blockchain data. 
The event streamer is the Swiss Army Knife we use to filter, transform, enrich the data coming from the [Web3 Monitoring Agent](https://github.com/keyko-io/web3-monitoring-agent).

## How to start

This project generates a java library that you can embedded in your java/scala project in order to facilitate your data transformations.

If you want to use the library, you can include the dependency in your project directly:

```xml
<dependency>
  <groupId>io.keyko.monitoring</groupId>
  <artifactId>web3-event-streamer</artifactId>
  <version>0.1.0</version>
</dependency>
```

If you want to run the application as a stand-alone process, you can compile and run it using the following commands:

```bash
mvn clean package
java -jar target/keyko-event-streamer-0.1.0-allinone.jar 
```

## License


```
Copyright 2020 Keyko GmbH

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
