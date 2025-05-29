CREATE TABLE agent_status (
	agent_id INT PRIMARY KEY,
	agent_name VARCHAR(100),
	status VARCHAR(20),
	logged_in_at TIMESTAMP,
	on_break_since TIMESTAMP
);

COMMENT ON COLUMN agent_status.status IS 'Allowed values are "On Call", "Available" and "Break"';

CREATE TABLE agent_performance (
	agent_id INT PRIMARY KEY,
	calls_handled INT,
	aht FLOAT,
	break_time FLOAT,
	csat FLOAT,
	login_duration FLOAT
);

CREATE TABLE queue_status (
	queue_name VARCHAR(50) PRIMARY KEY,
	waiting_calls INT,
	average_wait INT,
	longest_wait INT,
	sla_met FLOAT
);

CREATE TABLE call_logs (
	call_id UUID PRIMARY KEY,
	agent_id INT,
	queue VARCHAR(50),
	start_time TIMESTAMP,
	end_time TIMESTAMP,
	duration INT,
	type VARCHAR(20),
	status VARCHAR(20)
);

COMMENT ON COLUMN call_logs.type IS 'Allowed values are "inbound" and "outbound"';
COMMENT ON COLUMN call_logs.status IS 'Allowed values are "completed", "dropped", "queued" and "on going")';