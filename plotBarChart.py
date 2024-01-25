import pandas as pd
import matplotlib.pyplot as plt

# Data
data = {
    'Round': [1, 2, 3, 4, 5, 6, "Bonus moves GS1", 7, 8, 9, 10, "Bonus moves GS2", 11, 12, 13, 14],
    'Little Gritty':[4, 7, 8, 14, 16, 23, 25, 30, 33, 38, 40, 43, 44, 48, 50, 56],
    'Lady Silver':[4, 5, 11, 17, 20, 23, 23, 25, 30, 31, 31, 35, 39, 43, 44, 44],
    'Rudy Red':[3, 6, 12, 13, 18, 20, 20, 21, 25, 31, 31, 40, 45, 48, 51, 51],
    'StripyToni':[1, 2, 4, 6, 9, 12, 12, 15, 20, 22, 22, 26, 32, 36, 41, 41]
}

# Create DataFrame
df = pd.DataFrame(data)

# Set the index to 'Round'
df.set_index('Round', inplace=True)

# Plot
df.plot(kind='bar', figsize=(10, 7))

# Set labels and title
plt.xlabel('Round')
plt.ylabel('Moves')
plt.title('Worms Moves Over Turns')

# Show the plot
plt.show()