import random
import csv
import argparse

parser = argparse.ArgumentParser()
parser.add_argument("--file", help="output file name", default="students.csv")

args = parser.parse_args()

def select_friends(current_index, students, distance):
    start = max(0, current_index - distance)
    end = min(len(students), current_index + distance)
    neighbors = students[start:end]
    neighbors.remove(students[current_index])
    return random.sample(neighbors, min(len(neighbors), 4))

students = []
# Define the schools, gender, academic and behavioral performance
schools = ['School A', 'School B', 'School C']
genders = ['MALE', 'FEMALE']
academic_performance = ['LOW', 'MEDIUM', 'HIGH']
behavioral_performance = ['LOW', 'MEDIUM', 'HIGH']
comments = ['good', 'average', 'bad', 'excellent', 'fair', 'poor', 'great', 'decent', 'awful', 'superb']

# Generate the students list with all the required data
for i in range(240):
    student = {}
    student['name'] = 'Student ' + str(i + 1)
    quotient, remainder = divmod(i, 80)
    student['school'] = schools[quotient]
    student['gender'] = random.choice(genders)
    student['academicPerformance'] = random.choice(academic_performance)
    student['behavioralPerformance'] = random.choice(behavioral_performance)
    student['comments'] = random.choice(comments)
    student['friend1'] = ''
    student['friend2'] = ''
    student['friend3'] = ''
    student['friend4'] = ''
    student['notWith'] = ''
    students.append(student)

# For each student select friends
for i in range(0, len(students)):
    student = students[i]
    friends = select_friends(i, students, 10)
    student['friend1'] = friends[0]['name']
    student['friend2'] = friends[1]['name']
    student['friend3'] = friends[2]['name']
    student['friend4'] = friends[3]['name']
    # For each selected friend, add the student to their friends list
    for friend in friends:
        if student['name'] not in [friend['friend1'], friend['friend2'], friend['friend3'], friend['friend4']]:
            if friend['friend1'] == '':
                friend['friend1'] = student['name']
            elif friend['friend2'] == '':
                friend['friend2'] = student['name']
            elif friend['friend3'] == '':
                friend['friend3'] = student['name']
            elif friend['friend4'] == '':
                friend['friend4'] = student['name']

# Write the students data to a CSV file
with open(args.file, 'w', newline='') as file:
    writer = csv.DictWriter(file, fieldnames=['name', 'school', 'gender', 'academicPerformance', 'behavioralPerformance', 'comments', 'friend1', 'friend2', 'friend3', 'friend4', 'notWith'])
    writer.writeheader()
    writer.writerows(students)

