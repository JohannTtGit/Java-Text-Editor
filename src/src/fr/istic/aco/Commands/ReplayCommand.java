package fr.istic.aco.Commands;

import fr.istic.aco.Recorder.Recorder;

public class ReplayCommand implements Command {
	
	private Recorder recorder;
	
	public ReplayCommand(Recorder recorder) {
		this.recorder = recorder;
	}

	@Override
	public void execute() {
		recorder.replay();
	}
}
