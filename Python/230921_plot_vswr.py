
import skrf as rf
from skrf import plotting
import matplotlib.pyplot as plt

spara1 = rf.Network('D:/OneDrive/MARKONE/0_Project/01_RTL8822CU/01D_측정데이터/230921_VSWR/230921_S1P/73_free.s1p')
spara2 = rf.Network('D:/OneDrive/MARKONE/0_Project/01_RTL8822CU/01D_측정데이터/230921_VSWR/230921_S1P/73_jig.s1p')


spara1.frequency.unit='ghz'
spara2.frequency.unit='ghz'


fig, ax = plt.subplots()
spara1.plot_s_vswr(m=0, n=0, linewidth = 1, linestyle = '--', color='blue', 
                   label = 'Free state', title='#73')
spara2.plot_s_vswr(m=0,n=0, label = 'On the jig', color='red')

# set display and fontsizes
ax.grid()
ax.set_xlabel('Freq. (GHz)', fontsize=14)
ax.set_ylabel('VSWR', fontsize=14)
ax.tick_params(axis='x', labelsize=12)
ax.tick_params(axis='y', labelsize=12)

# plt.scatter([2.4, 2.44, 2.48, 5.15, 5.5, 5.85] )
# spara1['2.5']
# plotting.add_markers_to_lines(['2.4', '2.6'])

# plotting.add_markers_to_lines()

# spara1.plot_s_vswr(['2.4', '2.44', '2.48', '5.15', '5.5', '5.85'] )

plt.scatter([2.4*10**9, 2.48*10**9], [5, 6], color = 'r', s = 10)
# plt.text([2.4, 2.48], [5, 6], ['2.4', '2.48'])
spara2.plot_s_vswr(['5','5.5'])

plt.ylim([1, 11])
plt.show()