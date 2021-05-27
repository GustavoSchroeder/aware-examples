import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(new MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {
  static const platform = const MethodChannel('ch/aware');

  @override
  void initState() {
    super.initState();
    teste();
  }

  void init() async {
    await platform.invokeMethod('init');
  }

  void teste() async {
    // String res = await platform.invokeMethod('accelerometer');
    // print(res);
    // String res1 = await platform.invokeMethod('battery');
    // print('Bateria... ');
    // print(res1);
    // String res2 = await platform.invokeMethod('schedule');
    // print(res2);
    // String res3 = await platform.invokeMethod('sync');
    // print(res3);
    String res4 = await platform.invokeMethod('application');
    print(res4);
  }

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      home: new Scaffold(
          appBar: new AppBar(
            title: const Text('Plugin Example App'),
          ),
          body: Container(
            child: RaisedButton(
              onPressed: () {
                teste();
              },
              child: Text('teste'),
            ),
          )),
    );
  }
}
