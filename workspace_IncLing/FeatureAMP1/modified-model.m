FEATUREAMP___ : BASE___ :: _FEATUREAMP___ ;

BASE___ : GUI___ SUPPORTEDFORMATS___+ :: _Base ;

GUI___ : RESIZABLE___ ID3INFORMATION___ OPENFILE___ [VOLUMECONTROL___] [SHOWCOVER___] TIME___+ SKINS___ [PLAYLIST___] :: _GUI___ ;

VOLUMECONTROL___ : [MUTE___] :: _VOLUMECONTROL___ ;

TIME___ : SHOWTIME___
	| PROGRESSBAR___ ;

SKINS___ : LIGHT___
	| DARK___ ;

PLAYLIST___ : LOADFOLDER___ [SAVEANDLOADPLAYLIST___] CONTROL___+ [QUEUETRACK___] [CLEARPLAYLIST___]:: _PLAYLIST___ ;

CONTROL___ : SKIPTRACK___ 
	| SHUFFLEREPEAT___  
	| REORDERPLAYLIST___ 
	| REMOVETRACK___ ;



SUPPORTEDFORMATS___ : MP3___
	| WAV___ ;

%%

PLAYLIST___ implies PROGRESSBAR___ ;
CLEARPLAYLIST___ implies REMOVETRACK___ ;