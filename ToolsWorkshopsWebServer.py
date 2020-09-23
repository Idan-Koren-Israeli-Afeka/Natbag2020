#!/usr/bin/python
import subprocess

from flask import Flask, request

app = Flask("natbag2020_app")

@app.route("/departures")
def dep():
    return subprocess.check_output(["java", "-classpath",
                                    r"D:\Documents\Projects\ToolsWorkshop\Natbag2020\bin", "toolsWorkshop.Main",
                                    request.args.get('outformat'), "departures",
                                    request.args.get('airline'), request.args.get('country'),
                                    request.args.get('city'), request.args.get('airport'),
                                    request.args.get('day1'), request.args.get('month1'),
                                    request.args.get('year1'), request.args.get('day2'),
                                    request.args.get('month2'), request.args.get('year2'),
                                    request.args.get('sunday'), request.args.get('monday'),
                                    request.args.get('tuesday'), request.args.get('wednesday'),
                                    request.args.get('thursday'), request.args.get('friday'),
                                    request.args.get('saturday')])

@app.route("/arrivals")
def arr():
    return subprocess.check_output(["java", "-classpath",
                                    r"D:\Documents\Projects\ToolsWorkshop\Natbag2020\bin", "toolsWorkshop.Main",
                                    request.args.get('outformat'), "arrivals",
                                    request.args.get('airline'), request.args.get('country'),
                                    request.args.get('city'), request.args.get('airport'),
                                    request.args.get('day1'), request.args.get('month1'),
                                    request.args.get('year1'), request.args.get('day2'),
                                    request.args.get('month2'), request.args.get('year2'),
                                    request.args.get('sunday'), request.args.get('monday'),
                                    request.args.get('tuesday'), request.args.get('wednesday'),
                                    request.args.get('thursday'), request.args.get('friday'),
                                    request.args.get('saturday')])

app.run(port=8000, host="0.0.0.0")


# http://localhost:8000/departures?outformat=html&country=&city=&airport=&airline=&day1=&month1=&year1=&day2=&month2=&year2=&sunday=true&monday=true&tuesday=true&wednesday=true&thursday=true&friday=true&saturday=true
# http://localhost:8000/arrivals?outformat=html&country=&city=&airport=&airline=&day1=&month1=&year1=&day2=&month2=&year2=&sunday=true&monday=true&tuesday=true&wednesday=true&thursday=true&friday=true&saturday=true

